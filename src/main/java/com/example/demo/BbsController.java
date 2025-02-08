package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import jakarta.annotation.PostConstruct;
import java.util.Optional;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class BbsController {
    
    @Autowired
    BbsRepository repos;

    @Autowired
    ReplyRepository replyRepository;

    // 一覧(初期画面)
    @GetMapping("/")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView();
        List<BulletinBoad> list = repos.findAll();
        mav.setViewName("bbs/list");
        mav.addObject("data", list);
        return mav;
    }

    // 編集ページに遷移
    @GetMapping("/edit")
    public ModelAndView edit(@RequestParam int id) {
        ModelAndView mav = new ModelAndView();
        Optional<BulletinBoad> data = repos.findById(id);
        //もしdataが無かったら新規でentityに返す
        BulletinBoad entity = data.orElse(new BulletinBoad());
        mav.setViewName("bbs/edit");
        mav.addObject("bbsModel", entity); 
        return mav;
    }
    
    // 返信ページに遷移
    @GetMapping("/reply")
    public ModelAndView reply(@RequestParam int id) {
        ModelAndView mav = new ModelAndView();
        Optional<BulletinBoad> data = repos.findById(id);
        if (data.isPresent()) {
            BulletinBoad bulletinBoad = data.get();
            mav.addObject("bbsModel", bulletinBoad);

            // 返信データを取得してビューに渡す
            List<Reply> replies = replyRepository.findByParentBoad(bulletinBoad);
            mav.addObject("replies", replies);
        }
        mav.setViewName("bbs/reply");
        return mav;
    }

    // 返信登録後のリダイレクト処理
    @PostMapping("/saveReply")
    @Transactional
    public ModelAndView saveReply(@RequestParam int bulletinBoardId, @RequestParam String createUser, @RequestParam String replyMessage) {
    	try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            // Replyにデータをセット
            Reply reply = new Reply();
            reply.setCreateUser(createUser);
            reply.setReplyMessage(replyMessage);
            reply.setCreateDate(sdf.format(date));

            // 返信がどの投稿に対するものかを探して、無ければ例外へ
            BulletinBoad parentBoad = repos.findById(bulletinBoardId).orElseThrow(() -> new IllegalArgumentException("指定された投稿が見つかりません"));
            reply.setParentBoad(parentBoad);
            // 保存
            replyRepository.save(reply);
            // リダイレクト
            return new ModelAndView("redirect:/reply?id=" + parentBoad.getId());
    	} catch(IllegalArgumentException e) {
    		// エラーページへ遷移
            ModelAndView mav = new ModelAndView("errorPage");
            mav.addObject("errorMessage", e.getMessage());
            return mav;
    	}

    }

    // 更新処理(書き込み処理)
    @PostMapping("/save")
    @Transactional(readOnly=false)
    public ModelAndView save(@ModelAttribute("bbsModel") BulletinBoad data) {
        Date date = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
        data.setCreateDate(sdf1.format(date));
        repos.saveAndFlush(data);
        return new ModelAndView("redirect:/");
    }

    // 削除処理
    @PostMapping("/delete")
    @Transactional(readOnly=false)
    public ModelAndView delete(@RequestParam int id) {
        repos.deleteById(id);
        return new ModelAndView("redirect:/");
    }

    // 初期データ
    @PostConstruct
    public void init() {
        BulletinBoad data_1 = new BulletinBoad();
        data_1.setCreateDate("2024/01/01");
        data_1.setTitle("初期データタイトル１");
        data_1.setContent("初期データ書き込み１");
        data_1.setCreateUser("テストユーザー１");
        repos.saveAndFlush(data_1);

        BulletinBoad data_2 = new BulletinBoad();
        data_2.setCreateDate("2024/01/02");
        data_2.setTitle("初期データタイトル2");
        data_2.setContent("初期データ書き込み2");
        data_2.setCreateUser("テストユーザー2");
        repos.saveAndFlush(data_2);
    }
    
    
}

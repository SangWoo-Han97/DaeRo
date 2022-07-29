package com.ssafy.daero.sns.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.daero.sns.service.SnsService;
import com.ssafy.daero.sns.vo.ReplyVo;
import com.ssafy.daero.user.service.JwtService;
import org.apache.ibatis.annotations.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(value = "/sns")
public class SnsController {
    private final String SUCCESS = "SUCCESS";
    private final String FAILURE = "FAILURE";
    private final SnsService snsService;
    private final JwtService jwtService;

    public SnsController(SnsService snsService, JwtService jwtService) { this.snsService = snsService; this.jwtService = jwtService; }

    @GetMapping("/article/{article_seq}")
    public ResponseEntity<Map<String, Object>> articleDetail(@PathVariable int article_seq) throws JsonProcessingException {
        Map<String, Object> res = snsService.articleDetail(article_seq);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/article/{article_seq}")
    public ResponseEntity<String> deleteArticle(@RequestHeader Map<String, String> header, @PathVariable int article_seq) {
        String userJwt = header.get("jwt");
        Map<String, String> currentUser = jwtService.decodeJwt(userJwt);
        if(Objects.equals(currentUser.get("user_seq"), "null")) { return new ResponseEntity<>(FAILURE, HttpStatus.UNAUTHORIZED); }

        Integer res = snsService.deleteArticle(article_seq, Integer.parseInt(currentUser.get("user_seq")));
        if (res == 99) { return new ResponseEntity<>(FAILURE, HttpStatus.UNAUTHORIZED); }
        else if (res == 0 | res == null) { return new ResponseEntity<>(FAILURE, HttpStatus.BAD_REQUEST); }
        else {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }
    }

    @GetMapping("/article/{article_seq}/reply")
    public ResponseEntity<ArrayList<Map<String, Object>>> replyList(@PathVariable int article_seq, @RequestParam(defaultValue = "1") String page) {
        ArrayList<Map<String, Object>> res = snsService.replyList(article_seq, page);
        if (res == null) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
        else if (res.size() == 0) { return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
        else { return new ResponseEntity<>(res, HttpStatus.OK); }
    }

    @PostMapping("/article/{article_seq}/reply")
    public ResponseEntity<String> createReply(@RequestHeader Map<String, String> header, @PathVariable int article_seq, @RequestBody Map<String, String> req) {
        String userJwt = header.get("jwt");
        Map<String, String> currentUser = jwtService.decodeJwt(userJwt);
        if (Objects.equals(currentUser.get("user_seq"), "null")) {
            return new ResponseEntity<>(FAILURE, HttpStatus.UNAUTHORIZED);
        }
        ReplyVo replyVo = snsService.createReply(article_seq, Integer.parseInt(currentUser.get("user_seq")), req.get("content"));
        if (replyVo.getResult() == ReplyVo.ReplyResult.NO_SUCH_ARTICLE) { return new ResponseEntity<>(FAILURE, HttpStatus.BAD_REQUEST); }
        else { return new ResponseEntity<>(SUCCESS, HttpStatus.OK); }

    }

    @PutMapping("/reply/{reply_seq}")
    public ResponseEntity<String> updateReply(@RequestHeader Map<String, String> header, @PathVariable int reply_seq, @RequestBody Map<String, String> req) {
        String userJwt = header.get("jwt");
        Map<String, String> currentUser = jwtService.decodeJwt(userJwt);
        if (Objects.equals(currentUser.get("user_seq"), "null")) {
            return new ResponseEntity<>(FAILURE, HttpStatus.UNAUTHORIZED);
        }
        ReplyVo replyVo = snsService.updateReply(Integer.parseInt(currentUser.get("user_seq")), reply_seq, req.get("content"));
        if (replyVo.getResult() == ReplyVo.ReplyResult.NO_SUCH_REPLY) { return new ResponseEntity<>(FAILURE, HttpStatus.BAD_REQUEST); }
        else if (replyVo.getResult() == ReplyVo.ReplyResult.UNAUTHORIZED ) { return new ResponseEntity<>(FAILURE, HttpStatus.UNAUTHORIZED); }
        else { return new ResponseEntity<>(SUCCESS, HttpStatus.OK); }
    }

    @DeleteMapping("/reply/{reply_seq}")
    public ResponseEntity<String> deleteReply(@RequestHeader Map<String, String> header, @PathVariable int reply_seq) {
        String userJwt = header.get("jwt");
        Map<String, String> currentUser = jwtService.decodeJwt(userJwt);
        if (Objects.equals(currentUser.get("user_seq"), "null")) {
            return new ResponseEntity<>(FAILURE, HttpStatus.UNAUTHORIZED);
        }
        ReplyVo replyVo = snsService.deleteReply(Integer.parseInt(currentUser.get("user_seq")), reply_seq);
        if (replyVo.getResult() == ReplyVo.ReplyResult.NO_SUCH_REPLY) { return new ResponseEntity<>(FAILURE, HttpStatus.BAD_REQUEST); }
        else if (replyVo.getResult() == ReplyVo.ReplyResult.UNAUTHORIZED ) { return new ResponseEntity<>(FAILURE, HttpStatus.UNAUTHORIZED); }
        else { return new ResponseEntity<>(SUCCESS, HttpStatus.OK); }
    }
}
package com.sports.action;

import com.sports.model.FriendRequestVO;
import com.sports.model.UserVO;
import com.sports.service.IUserService;
import com.sports.utils.BaseController;
import com.sports.utils.CollectionUtils;
import com.sports.utils.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by Gundam on 2016/10/25.
 */
@Controller
@RequestMapping("/user")
public class UserAction extends BaseController {
    @Autowired
    @Qualifier("userService")
    private IUserService userService;

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public
    @ResponseBody
    String login(String loginName, String password) {
        UserVO userInfo = userService.findUserByPwd(loginName, password);
        if (userInfo != null) {
            Map<String, String> result = new HashMap<>();
            result.put("result", GsonUtils.getInstance().toJson(userInfo));
            return CollectionUtils.getOutCome(SUCCESS, LOGINSUCCESSMESSAGE, result);
        } else {
            return CollectionUtils.getOutCome(FAILED, LOGINFAILEDMESSAGE, EMPTYRESULT);
        }
//        if(loginName != null && loginName.equals("admin") && password != null && password.equals("123456") ){
//            return CollectionUtils.getOutCome(SUCCESS,LOGINSUCCESSMESSAGE,EMPTYRESULT);
//        }else{
//            return CollectionUtils.getOutCome(FAILED,LOGINFAILEDMESSAGE,EMPTYRESULT);
//        }
    }

    @RequestMapping(value = "/checkuserexisted", method = RequestMethod.GET)
    public
    @ResponseBody
    String checkUserNameExisted(@RequestParam("userName") String userName){
        if (userService.isExist(userName)) {
            return CollectionUtils.getOutCome(FAILED, USER_EXISTED_MESSAGE, EMPTYRESULT);
        } else {
            return CollectionUtils.getOutCome(SUCCESS, OPERATIONSUCCESSMESSAGE, EMPTYRESULT);
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public
    @ResponseBody
    String register(@RequestParam String userJson) {
        UserVO userVO = GsonUtils.getInstance().fromJson(userJson, UserVO.class);
        if (userService.register(userVO)) {
            Map<String, String> result = new HashMap<>();
            result.put("result", GsonUtils.getInstance().toJson(userVO));
            return CollectionUtils.getOutCome(SUCCESS, OPERATIONSUCCESSMESSAGE, result);
        } else return CollectionUtils.getOutCome(SUCCESS, OPERATIONFAILEDMESSAGE, EMPTYRESULT);
    }

    @RequestMapping(value = "/updatesportslike", method = RequestMethod.POST)
    public
    @ResponseBody
    String changeLike(String userId, String sportsLike) {
        //String[] likes = sportsLike.split(",");
        if (userService.setUserLikes(userId, sportsLike)) {
            return CollectionUtils.getOutCome(SUCCESS, OPERATIONSUCCESSMESSAGE, EMPTYRESULT);
        } else return CollectionUtils.getOutCome(SUCCESS, OPERATIONFAILEDMESSAGE, EMPTYRESULT);
    }

    @RequestMapping(value = "/getfriendrequests", method = RequestMethod.POST)
    public
    @ResponseBody
    String getFriendRequests(@RequestParam String user_id, String page_size, String current_page) {

        Map<String, String> result = new HashMap<>();
        result.put("result", GsonUtils.getInstance().toJson(userService.getFriendRequests(user_id, page_size, current_page)));
        result.put("total_size", String.valueOf(userService.getFriendRequestsCount(user_id)));
        return CollectionUtils.getOutCome(SUCCESS, LOGINSUCCESSMESSAGE, result);

    }

    @RequestMapping(value = "/addfriend", method = RequestMethod.POST)
    public
    @ResponseBody
    String addFriend(@RequestParam String friend_request) {
        FriendRequestVO requestVO = GsonUtils.getInstance().fromJson(friend_request, FriendRequestVO.class);
        if (userService.createFriendRequests(requestVO)) {
            return CollectionUtils.getOutCome(SUCCESS, OPERATIONSUCCESSMESSAGE, EMPTYRESULT);
        } else return CollectionUtils.getOutCome(SUCCESS, OPERATIONFAILEDMESSAGE, EMPTYRESULT);
    }




    @RequestMapping(value = "/test")
    public
    @ResponseBody
    String test() {
        String result = CollectionUtils.getOutCome(SUCCESS, LOGINSUCCESSMESSAGE, EMPTYRESULT);
        return result;
    }
}

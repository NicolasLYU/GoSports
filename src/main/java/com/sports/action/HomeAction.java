package com.sports.action;

import com.sports.model.SportsFirstClass;
import com.sports.service.IHomeService;
import com.sports.service.ISportEventService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qifan on 2016/12/2.
 */
@Controller
@RequestMapping("/home")
public class HomeAction extends BaseController{
    @Autowired
    @Qualifier("HomeService")
    private IHomeService homeService;

    private void setHomeService(IHomeService homeService){this.homeService=homeService;}

    @Autowired
    @Qualifier("sportsEventService")
    ISportEventService sportEventService;


    @RequestMapping(value = "/sportevents", method = RequestMethod.GET)
    public
    @ResponseBody
    String getHomeSportEvent(@RequestParam("user_like") String user_like) {
        List<SportsFirstClass> sportsFirstClasses = sportEventService.getSportEventsByType(user_like);
        Map<String, Object> result = new HashMap<>();
        result.put("result", GsonUtils.getInstance().toJson(sportsFirstClasses));
        //result.put("result", sportEventService.getSportEventsByType(user_like));
        return CollectionUtils.getOutComeObject(SUCCESS, OPERATIONSUCCESSMESSAGE, result);
    }
}

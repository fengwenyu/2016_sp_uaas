package com.shangpin.uaas.controller.admin;

import org.springframework.stereotype.Controller;
@Controller
public class MemcachedController {
   /* @Autowired
    private MemcachedClient memcachedClient;
    def index() {
        render(view: "test1")
    }
    
    

    def testA() {
        log.debug("========================" + params["key"])
        String token = params["key"] == null ? "token" : params["key"]
        if (token != null) {
            Subject subject = new Subject()
            subject.userId = params["key"]
            memcachedClient.set(token, 1200, subject)
        }
        Object value = memcachedClient.get(token)
        Map<String, String> test = new HashMap<String, String>()
        test.put("value", value.userId)
//        test.put("value", value)
        log.debug("获取到值为："  + value)
        render(model: test)
    }

    def testB() {
        log.debug("========================" + params["token"])
        String key = (String)params["token"]
        log.debug("===========key=============" + key)
        Object value = memcachedClient.get(key)

        log.debug("获取到得结果是" + value)

        Map<String, String> test = new HashMap<String, String>()
        test.put("value", value.userId)
//        test.put("value", value)
        log.debug("获取到值为："  + value)
        render(model: test)
    }

    def testC() {
        log.debug("==============" + params["token"])

    }*/

}

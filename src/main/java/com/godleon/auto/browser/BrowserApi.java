package com.godleon.auto.browser;


import com.godleon.auto.container.AutoContainerMain;
import com.godleon.auto.entity.Task;
import com.godleon.auto.socket.WebSocketServer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Browser operation
 * @author leon
 * @date 2021-06-21
 */
@RestController
@RequestMapping("/api")
public class BrowserApi {

    /**
     * Open the browser
     */
    @PostMapping("/start")
    public void startBrowser(@RequestBody String content){
        AutoContainerMain.runTask(content);
    }

    /**
     * buy operation
     */
    @PostMapping("/buy")
    public void buy(@RequestBody Task task){
        AutoContainerMain.runBuy(task);
    }

    /**
     * buy operation
     */
    @PostMapping("/singleBuy")
    public void singleBuy(@RequestBody Task task){
        AutoContainerMain.runSingleBuy(task);
    }

}

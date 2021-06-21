package com.godleon.auto.browser;


import com.godleon.auto.container.AutoContainerMain;
import com.godleon.auto.entity.Task;
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
    public void startBrowser(@RequestBody Task task){
        AutoContainerMain.runTask(task);
    }

}

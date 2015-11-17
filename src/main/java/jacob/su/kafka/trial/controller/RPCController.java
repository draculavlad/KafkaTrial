package jacob.su.kafka.trial.controller;

import jacob.su.kafka.trial.rpc.RPCProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>TODO</p>
 *
 * @author <a href="mailto:ysu2@cisco.com">Yu Su</a>
 * @version 1.0
 */
@Controller
@RequestMapping("/rpc")
public class RPCController {

    @Autowired
    private RPCProcessor processor;

    @RequestMapping(value = "/put", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void rpcPutCall(@RequestParam("message") String msg) {
        processor.sendMsg(msg);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public Object rpcGetCall() {
        return processor.getMsg();
    }
}

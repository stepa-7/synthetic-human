package com.stepa7.bishop;

import com.stepa7.starter.android.Android;
import com.stepa7.starter.android.AndroidService;
import com.stepa7.starter.command.Command;
import com.stepa7.starter.command.CommandSender;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping("/api/v1")
public class BishopController {
    private final CommandSender commandSender;
    private final AndroidService androidService;

    public BishopController(CommandSender commandSender, AndroidService androidService) {
        this.commandSender = commandSender;
        this.androidService = androidService;
    }

    @PutMapping("/command")
    public void putCommand(@Valid @RequestBody Command command) throws InterruptedException {
        commandSender.sendCommand(command);
    }

    @PostMapping("/android")
    public void createAndroid(@Valid @RequestBody Android android) {
        androidService.registerAndroid(android);
    }

    @GetMapping("/android")
    public ConcurrentMap<Long, Android> getAndroids() {
         return androidService.getAndroids();
    }
}

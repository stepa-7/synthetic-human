package com.stepa7.bishop;

import com.stepa7.starter.command.Command;
import com.stepa7.starter.command.CommandSender;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/command")
public class BishopController {
    @Autowired
    private final CommandSender commandSender;

    public BishopController(CommandSender commandSender) {
        this.commandSender = commandSender;
    }

    @PutMapping
    public void putCommand(@Valid @RequestBody Command command) throws InterruptedException {
        commandSender.sendCommand(command);
    }
}

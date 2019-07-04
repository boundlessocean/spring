package com.controller.Config;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

@Component
public class AppExit implements ExitCodeGenerator {
    @Override
    public int getExitCode() {
        return 140;
    }
}

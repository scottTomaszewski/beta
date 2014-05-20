package com.beta;

import io.dropwizard.setup.Environment;

public interface Api {
    void run(Main.BetaConfig cfg, Environment env) throws ClassNotFoundException;
}

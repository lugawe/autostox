package org.autostox.server;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class AutostoxServer {

    public AutostoxServer() {}

    static void main(String[] args) {
        Quarkus.run(args);
    }
}

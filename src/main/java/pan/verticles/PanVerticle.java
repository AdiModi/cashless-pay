package pan.verticles;

import com.englishtown.vertx.hk2.HK2JerseyBinder;
import com.englishtown.vertx.hk2.HK2VertxBinder;
import com.englishtown.vertx.jersey.JerseyServer;
import constants.ResourcesPath;
import generics.JsonFileReader;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class PanVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(PanVerticle.class);

    private JsonObject configJson, jerseyConfig;

    public PanVerticle() {
        this(ResourcesPath.Configs.FILE_PAN_VERTICLE_CONFIG_PATH);
    }

    public PanVerticle(String configFilePath) {
        this.configJson = new JsonFileReader().readJson(new File(configFilePath));
        if (this.configJson == null) {
            LOGGER.error("Error Reading Config File, Quitting!");
            System.exit(1);
        }
        try {
            this.jerseyConfig = this.configJson.getJsonObject("jerseyConfig");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error Reading Jersey Config File, Quitting!");
        }
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {

        Router router = Router.router(vertx);
        router.route("/pan/*").handler(StaticHandler.create("assets"));

        vertx.runOnContext(aVoid -> {
            vertx.getOrCreateContext().config()
                    .put("jersey", configJson.getJsonObject("jerseyConfig"));

            ServiceLocator serviceLocator = ServiceLocatorUtilities.bind(new HK2JerseyBinder(), new HK2VertxBinder(vertx));
            JerseyServer jerseyServer = serviceLocator.getService(JerseyServer.class);

            LOGGER.info("Staring Jersey Server for {}", PanVerticle.class.getCanonicalName());
            jerseyServer.start();
        });
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        super.stop(stopFuture);
    }
}
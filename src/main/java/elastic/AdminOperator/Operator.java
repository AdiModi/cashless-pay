package elastic.AdminOperator;

import elastic.Client;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Operator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Operator.class);

    private RestHighLevelClient restHighLevelClient;

    public Operator() {
        LOGGER.info("Getting The Singleton Transport Client");
        restHighLevelClient = Client.getClient();
    }

    /*Create Functions here for Admin Operations*/
}
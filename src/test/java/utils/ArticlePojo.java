package utils;

import java.util.Date;

//// import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
//// import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
///* ObjectMapper om = new ObjectMapper();
//Root root = om.readValue(myJsonString, Root.class); */
public class ArticlePojo {
    public String author;
    public String title;
    public int article_id;
    public String topic;
    public Date created_at;
    public int votes;
    public String article_img_url;
    public String comment_count;
    public int total_count;
}


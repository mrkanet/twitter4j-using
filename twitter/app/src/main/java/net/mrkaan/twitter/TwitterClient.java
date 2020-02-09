package net.mrkaan.twitter;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.StrictMode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterClient extends AppCompatActivity {
    ConfigurationBuilder cb;
    TwitterFactory tf;
    Twitter twitter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build()); //bu olmazsa bağlantı hatası verir. oncreate in ilk satırında olması şart
        super.onCreate(savedInstanceState, persistentState);

        ResponseList<Status> statuses = null;
        try {
            //bağlantı için gerekli bilgiler set ediliyor
            //farklı yöntemler ile de set edilme yapılabilir
            cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey()
                    .setOAuthConsumerSecret()
                    .setOAuthAccessToken()
                    .setOAuthAccessTokenSecret89();
            //bağlantı kurulup istek atmak için kullanılacak nesne çağrılıyor
            //twitter = (new TwitterFactory(cb.build())).getInstance(); // bu da çalışıyor
            tf = new TwitterFactory(cb.build());
            twitter = tf.getInstance();
            //anasayfadaki tweetler çekiliyor
            statuses = twitter.getHomeTimeline();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // buradan sonrası önemli değil yukarıda timeline çkildi burası stringe dönüştürüyor mesajları
        StringBuilder statusesStr = new StringBuilder();
        assert statuses != null;
        for (Status status : statuses) {
            statusesStr.append(status.getText()).append("\n");
        }

    }

    //search yapmak için burayı kullanabilirsin. queryStr objesi aranacak kelime
    //dönen string ise ortaya çıkan sonuçlardan çıkan mesajların oluşturduğu string
    private String search(String queryStr) {
        Query query = new Query(queryStr);
        QueryResult result = null;
        try {
            result = twitter.search(query);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        StringBuilder statusStr = new StringBuilder();
        assert result != null;
        for (Status status : result.getTweets()) {
            statusStr.append(status.getText()).append("\n");
        }
        return statusStr.toString();
    }


}

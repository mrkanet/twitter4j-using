package net.mrkaan.twitter

import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import twitter4j.*
import twitter4j.conf.ConfigurationBuilder


class MainActivity : AppCompatActivity() {
    var cb: ConfigurationBuilder? = null
    var tf: TwitterFactory? = null
    var twitter: Twitter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build()) //bu olmazsa bağlantı hatası verir
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lateinit var statuses: ResponseList<Status?>

        runOnUiThread {
            try {
                cb = ConfigurationBuilder()
                cb!!.setDebugEnabled(true)
                    .setOAuthConsumerKey("")
                    .setOAuthConsumerSecret("")
                    .setOAuthAccessToken("")
                    .setOAuthAccessTokenSecret("")
                tf = TwitterFactory(cb!!.build())
                twitter = tf!!.getInstance()
                //val twitter = TwitterFactory.getSingleton()
                statuses = twitter!!.homeTimeline
            } catch (ex: Exception) {
                text.text = "There is an exception: \n${ex.message}"
            }
        }

        var statusesStr = ""
        for (status in statuses) {
            statusesStr += status!!.text
            statusesStr += "\n"
        }
        text.text = statusesStr
        btn_search.setOnClickListener{
            searchTweet()
        }
    }

    fun searchTweet(){
        var statusesStr = ""
        runOnUiThread{
            //val twitter = TwitterFactory.getSingleton()
            val query = Query(input_search.text.toString())
            val result = twitter!!.search(query)
            for (status in result.tweets) {
                statusesStr += "${status!!.text}\n"
            }
        }
        text.text = statusesStr
    }
}

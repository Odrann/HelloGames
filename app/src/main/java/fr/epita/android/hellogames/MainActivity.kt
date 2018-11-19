package fr.epita.android.hellogames


import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var gameList = arrayListOf<Game>()
    var selectedGameList = mutableListOf<Game>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firstGame.setOnClickListener(this@MainActivity)
        secondGame.setOnClickListener(this@MainActivity)
        thirdGame.setOnClickListener(this@MainActivity)
        fourthGame.setOnClickListener(this@MainActivity)
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()
        val service: WebServiceInterface = retrofit.create(WebServiceInterface::class.java)
        val callback = object : Callback<List<Game>> {
            override fun onFailure(call: Call<List<Game>>?, t: Throwable?) {
                Log.d("TAG", "FAILED")
            }
            override fun onResponse(call: Call<List<Game>>?,
                                    response: Response<List<Game>>?) {
                if (response != null) {
                    if (response.code() == 200) {
                        val responseData = response.body()
                        if (responseData != null) {
                            gameList.addAll(responseData)
                            selectedGameList = getRandomGame(gameList)
                            setInfo()
                        }
                    }
                }
            }
        }
        service.listofGame().enqueue(callback)
    }
    fun getRandomGame(gameList: MutableList<Game>): MutableList<Game>
    {
        var selectedData: MutableList<Game> = mutableListOf<Game>()
        for (i in 0..3)
        {
            var game = gameList.removeAt(Random().nextInt(gameList.size))
            selectedData.add(game)
        }
        return selectedData
    }
    override fun onClick(clickedView: View?) {
        if (clickedView != null) {
            when (clickedView.id) {
                R.id.firstGame -> {
                    var gameId = selectedGameList.get(0).id
                    sendIntent(gameId!!, getImageId(gameId!!))
                }
                R.id.secondGame -> {
                    var gameId = selectedGameList.get(1).id
                    sendIntent(gameId!!, getImageId(gameId!!))
                }
                R.id.thirdGame -> {
                    var gameId = selectedGameList.get(2).id
                    sendIntent(gameId!!, getImageId(gameId!!))
                }
                R.id.fourthGame -> {
                    var gameId = selectedGameList.get(3).id
                    sendIntent(gameId!!, getImageId(gameId!!))
                }
            }
        }
    }
    fun setInfo() {
        findViewById<ImageView>(R.id.firstGame).setImageResource(getImageId(selectedGameList.get(0).id!!))
        findViewById<ImageView>(R.id.secondGame).setImageResource(getImageId(selectedGameList.get(1).id!!))
        findViewById<ImageView>(R.id.thirdGame).setImageResource(getImageId(selectedGameList.get(2).id!!))
        findViewById<ImageView>(R.id.fourthGame).setImageResource(getImageId(selectedGameList.get(3).id!!))
    }
    fun sendIntent(gameId: Int, imageId: Int)
    {
        val CODE_GAMEID = "CODE_ID"
        val CODE_IMAGEID = "CODE_IMAGEID"
        val explicitIntent = Intent(this, SecondActivity::class.java)
        explicitIntent.putExtra(CODE_GAMEID, gameId)
        explicitIntent.putExtra(CODE_IMAGEID, imageId)
        startActivity(explicitIntent)
    }


    fun getImageId(gameId: Int): Int
    {
        var res: Int? = null
        when (gameId)
        {
            1 -> res = 0
            2 -> res = 1
            3 -> res = 2
            4 -> res = 3
            5 -> res = 4
            6 -> res = 5
            7 -> res = 6
            8 -> res = 7
            9 -> res = 8
            10 -> res = 9
        }
        return res!!
    }


}
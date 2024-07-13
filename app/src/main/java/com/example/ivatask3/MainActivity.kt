package com.example.ivatask3

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity() {

//    val url =assets.open("content.html").toString()
    val url ="https://www.developers.android.com"



    val client = OkHttpClient()
    val result = StringBuilder()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val TextViews: TextView = findViewById(R.id.first_word_count)
        val TextViews2 :TextView = findViewById(R.id.two_word_count) as TextView
        val TextViews3  :TextView= findViewById(R.id.third_word_count) as TextView



        // Fetch content
        CoroutineScope(Dispatchers.Main).launch {
            val content = withContext(Dispatchers.IO) {
                fetchContent(url)

            }
            val plainText = Jsoup.parse(content).text()
            println(plainText)


            val contentWithoutWhitespace = plainText.replace("\\s".toRegex(), "")

            if (contentWithoutWhitespace.length >= 10) {
                // Get the 10th character
                val tenthCharacter = contentWithoutWhitespace[11]
                println("The 10th character is: $tenthCharacter")
                TextViews.text = tenthCharacter.toString()

            } else {
                println("The content is less than 10 characters long.")
            }
//**********************************************************************************

            content?.let {
                // Process the content and update the UI
                val characters = processContent(it)
                TextViews2.text = "Characters at 10th, 20th, 30th :\n" +
                        characters.joinToString(separator = "\n") { "Position ${it.first}: ${it.second}" }
            } ?: run {
                TextViews2.text = "Fialed to get data."
            }

            // Split the content into words, ignoring case
            val words = plainText.split("\\W+".toRegex())
            val wordCount = mutableMapOf<String, Int>()

            // Count occurrences of each word
            for (word in words) {
                if (word.isNotBlank()) {
                    wordCount[word] = wordCount.getOrDefault(word, 0) + 1
                }
            }

            // Print the results
            for ((word, count) in wordCount) {
                result.append("$word: $count\n")

            }
            withContext(Dispatchers.Main) {
                TextViews3.text = result.toString()
            }
        }
    }

//       private fun fetchContent(url: String): String? {
//            val request = Request.Builder().url(url).build()
//            return try {
//                val response: Response = client.newCall(request).execute()
//                if (response.isSuccessful) {
//                    response.body?.string()
//
//                } else {
//                    null
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//                null
//            }
//        }



    // Suspend function to fetch content from the given URL
    private suspend fun fetchContent(url: String): String? {
        return withContext(Dispatchers.IO) {
            val request = Request.Builder().url(url).build()
            try {
                val response: Response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    response.body?.string()
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }


//    // Function to process content and get every 10th character
//    private fun processContent(content: String): List<Pair<Int, Char>> {
//        val contentWithoutWhitespace = content.replace("\\s".toRegex(), "")
//        val characters = mutableListOf<Pair<Int, Char>>()
//
//        for (i in 9 until contentWithoutWhitespace.length step 10) {
//            characters.add(Pair(i + 1, contentWithoutWhitespace[i]))
//        }
//
//        return characters
//    }

    // Function to process content and get every 10th character
    private fun processContent(content: String): List<Pair<Int, Char>> {
        val document = Jsoup.parse(content)
        val plainText = document.text()
        val contentWithoutWhitespace = plainText.replace("\\s".toRegex(), "")
        val characters = mutableListOf<Pair<Int, Char>>()

        for (i in 9 until contentWithoutWhitespace.length step 10) {
            characters.add(Pair(i + 1, contentWithoutWhitespace[i]))
        }

        return characters
    }

}
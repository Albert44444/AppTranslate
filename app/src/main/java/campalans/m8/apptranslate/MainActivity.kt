package campalans.m8.apptranslate

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import campalans.m8.apptranslate.API.retrofitService
import campalans.m8.apptranslate.ui.theme.DetecitonResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var btnDetectLanguage: Button
    private lateinit var etDescription:EditText
    private  lateinit var progressbar : ProgressBar
    var allLanguages = emptyList<Language>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initListener()
        getLanguage()

            }

    private fun initListener() {

        btnDetectLanguage.setOnClickListener{
            val text = etDescription.text.toString()
            if(text.isNotEmpty()){
                showLoading()
                getTextLanguage(text)
            }
        }
    }

    private  fun  showLoading()
    {
        progressbar.visibility = View.VISIBLE
    }





    private fun getTextLanguage(text: String) {
        CoroutineScope(Dispatchers.IO).launch{
            val result = retrofitService.getTextLanguage(text)
            if (result.isSuccessful){

                checkResult(result.body())
            }
            else{
                showError()
            }
            cleanText()
            hideLoading()
        }
    }

    private fun cleanText() {
        etDescription.setText("")
    }

    private fun hideLoading() {
        runOnUiThread {
            progressbar.visibility = View.GONE
        }
    }




    private fun getLanguage(){
        CoroutineScope(Dispatchers.IO).launch {
            val languages = retrofitService.getLaunguage()
            if (languages.isSuccessful)
            {
                allLanguages = languages.body()?: emptyList()
                showPas()

            }
            else
            {
                showError()
            }
        }

    }

    private fun checkResult(detecitonResponse: DetecitonResponse?) {

        if (detecitonResponse != null && !detecitonResponse.data.detections.isNullOrEmpty()) {
            val correctLanguage = detecitonResponse.data.detections.filter { it.isReadable }
            if (correctLanguage.isNotEmpty()) {

                val languageName = allLanguages.find { it.code == correctLanguage.first().language }

                if (languageName != null) {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "El idioma es ${languageName.name}", Toast.LENGTH_LONG).show()
                        Log.d("MainActivity", "languageName no null")
                    }
                }




            }
        }
    }




    private fun showError()
    {
        runOnUiThread{
            Toast.makeText(this,"Error al hacer la Llamada", Toast.LENGTH_SHORT).show()
        }

    }

    private  fun showPas() {
        runOnUiThread{
            Toast.makeText(this,"Pass al hacer la Llamada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initView() {
        btnDetectLanguage=findViewById(R.id.btnDetectLanguage)
        etDescription = findViewById(R.id.etDescription)
        progressbar = findViewById(R.id.progressbar)
    }
}


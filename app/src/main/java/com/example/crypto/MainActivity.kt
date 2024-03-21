package com.example.crypto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Header
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.crypto.databinding.ActivityMainBinding
import com.example.crypto.databinding.RvLayoutBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdapter: RvAdapter
    private lateinit var data:ArrayList<ViewModal>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        data = ArrayList<ViewModal>()
        apiData
        rvAdapter= RvAdapter(this,data)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = rvAdapter


        binding.searhcview.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                val filterdata = ArrayList<ViewModal>()
                for(i in data){
                    if(i.name.lowercase(Locale.getDefault()).contains(p0.toString().lowercase(Locale.getDefault()))){
                        filterdata.add(i)
                    }
                }

                if(filterdata.isEmpty()){
                    Toast.makeText(this@MainActivity, "No data Found", Toast.LENGTH_LONG).show()
                }else{
                    rvAdapter.changedata(filterdata)
                }
            }

        })


    }


    val apiData:Unit
        get() {

            val url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"
            val queue = Volley.newRequestQueue(this)


            val jsonObjectRequest:JsonObjectRequest = object:JsonObjectRequest(Method.GET, url , null, Response.Listener {
                response ->
                binding.progressBar.isVisible = false
                 try {
                     val ArrayData = response.getJSONArray("data")
                     for (i in 0 until ArrayData.length()){
                         val dataObject = ArrayData.getJSONObject(i)
                         val symbol = dataObject.getString("symbol")
                         val name = dataObject.getString("name")
                         val quote = dataObject.getJSONObject("quote")
                         val usd= quote.getJSONObject("USD")
                         val price = String.format("$ "+"%.2f",usd.getDouble("price") )

                         data.add(ViewModal(symbol,name,price.toString()))

                     }
                     rvAdapter.notifyDataSetChanged()
                 } catch (e:Exception){
                     Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                 }
            },Response.ErrorListener {
                Toast.makeText(this, "Error 1", Toast.LENGTH_LONG).show()
            }
            )
            {
                override fun getHeaders(): Map<String, String> {

                    val header = HashMap<String,String>()
                    header["X-CMC_PRO_API_KEY"] = "d14448e7-28a1-4516-9690-6c868b47f1c7"
                    return header
                }
            }
        queue.add(jsonObjectRequest)

        }




}
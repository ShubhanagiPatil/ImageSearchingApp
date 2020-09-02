package com.cavista.imagesearchingapp.Activities

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.cavista.imagesearchingapp.Database.DatabaseHandlerHandler
import com.cavista.imagesearchingapp.Model.ImageData
import com.cavista.imagesearchingapp.R

class ImageDetailActivity : AppCompatActivity() {


    @BindView(R.id.img_details)
    lateinit var image: ImageView

    @BindView(R.id.ed_comment)
    lateinit var edComment: EditText

    @BindView(R.id.btn_submit_comment)
    lateinit var btnSubmitComment: Button

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_details)
        ButterKnife.bind(this)
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val imagId = bundle.getString("imageId")
            val imageName = bundle.getString("imageName")
            val imageLink = bundle.getString("imageLink")

            val databaseHandler = DatabaseHandlerHandler(this, null, null, 1);
            databaseHandler.writableDatabase
            setSupportActionBar(toolbar)
            val actionBar = supportActionBar
            actionBar!!.title = imageName
            actionBar.setDisplayHomeAsUpEnabled(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.statusBarColor = Color.WHITE
            }

            Glide.with(this)
                    .load(imageLink)
                    .into(image)


            var findComment: String = ""
            if (imagId != null)
                findComment = databaseHandler.findImageComment(imagId)
            fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
            if (!findComment.equals(""))
                edComment.text = findComment.toEditable()


            btnSubmitComment.setOnClickListener(View.OnClickListener {
                val imageData = ImageData()
                imageData.id = imagId
                imageData.tital != title
                imageData.comment = edComment.text.toString()
                databaseHandler.addProduct(imageData)
                Toast.makeText(this,"Comment added successfully",Toast.LENGTH_LONG).show()

            })

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}
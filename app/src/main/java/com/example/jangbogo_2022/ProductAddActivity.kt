package com.example.jangbogo_2022

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.jangbogo_2022.databinding.ActivityProductAddBinding
import com.example.jangbogo_2022.network.MyApplication
import com.example.jangbogo_2022.util.dateToString
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ProductAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductAddBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var filePath: String //갤러리

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //sharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val bgColor = sharedPreferences.getString("tagColor", "#FFFFFFFF") // MySettingFragment에서 선택된 값을 가져옴!!
        binding.rootProductAdd.setBackgroundColor(Color.parseColor((bgColor)))

        binding.ivBack.setOnClickListener {
            finish()
        }

        // launcher
        val requestCameraFileLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            val calRatio = calculateInSampleSize(Uri.fromFile(File(filePath)), 150, 150) //사진 경로로부터 사진 가져와서 크기 조정
            val option = BitmapFactory.Options()
            option.inSampleSize = calRatio
            val bitmap = BitmapFactory.decodeFile(filePath, option) //비트맵으로 만듦
            bitmap?.let {
                binding.ivProductAdd.setImageBitmap(bitmap)
            } ?: let {
                Log.d("jh", "bitmap null")
            }

            // 이미지 가져왔으면 숨김
            binding.tvProductAddHow.visibility = View.GONE
            binding.btnProductAddCamera.visibility = View.GONE
            binding.btnProductAddGallery.visibility = View.GONE
        }
        val timeS: String = SimpleDateFormat("yyyymmdd_HHmmss").format(Date()) //시간 정보 가져와서 파일 이름에 사용할 것임
        val storeDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES) //파일 저장 경로
        val file = File.createTempFile("JPEG_${timeS}_",".jpg", storeDir) //파일 이름, 확장자, 저장경로를 넣어서 파일을 만듦
        filePath = file.absolutePath //만든 파일의 루트부터 시작하는 절대주소 경로를 얻음(다른 곳에서 사용함)
        val photoURI: Uri = FileProvider.getUriForFile(
            this,
            "com.example.jangbogo_2022.fileprovider",//manifest 의 authority
            file //만들어둔 파일. 여기서 uri가져와서 앱에서 사용
        )
        //카메라로 이미지 가져오기
        binding.btnProductAddCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI) //카메라앱은 찍은 사진을 이 uri(파일위치)에 저장해라
            requestCameraFileLauncher.launch(intent)
        }

        //갤러리 가서 사진 위치정보 가져오고 뷰에 넣기
        val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode === android.app.Activity.RESULT_OK){
                Glide
                    .with(applicationContext)
                    .load(it.data?.data) //이미지에 대한 위치 정보 가져옴
                    .apply(RequestOptions().override(150, 150))
                    .centerCrop()
                    .into(binding.ivProductAdd)
                val cursor = contentResolver.query(it.data?.data as Uri,
                    arrayOf<String>(MediaStore.Images.Media.DATA), null, null, null)
                cursor?.moveToFirst().let{
                    filePath = cursor?.getString(0) as String //사진 위치 정보 얻어서 저장!!
                }

                // 이미지 가져왔으면 숨김
                binding.tvProductAddHow.visibility = View.GONE
                binding.btnProductAddCamera.visibility = View.GONE
                binding.btnProductAddGallery.visibility = View.GONE
            }
        }
        //갤러리로 이미지 가져오기
        binding.btnProductAddGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            requestLauncher.launch(intent)
        }

        binding.btnProductAdd.setOnClickListener {
            if(binding.ivProductAdd.drawable !== null && binding.etProductAddName.text.isNotEmpty()){
                //TODO : 서버에 상품추가 요청
                Toast.makeText(this, "상품을 추가합니다. 잠시만 기다려주세요.", Toast.LENGTH_LONG).show()
                saveProduct()
            }
            else{
                Toast.makeText(this, "상품의 사진과 이름을 반드시 작성해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun saveProduct() {
        val data = mapOf(
            "uid" to MyApplication.auth.uid,
            "email" to MyApplication.email,
            "pName" to binding.etProductAddName.text.toString(),
            "pMemo" to binding.etProductAddMemo.text.toString()
//            "date" to dateToString(Date())
        )

        MyApplication.db.collection("product")
            .add(data)
            .addOnSuccessListener {
                uploadImage(it.id) //이미지를 storage에 올림
            }
            .addOnFailureListener {
                Log.d("jh", "doc save error ............")
            }
    }

    private fun uploadImage(docId: String) {
        val storage = MyApplication.storage
        val storageRef = storage.reference
        val imageRef = storageRef.child("images/product/${docId}.jpg")

        val file = Uri.fromFile(File(filePath))
        imageRef.putFile(file) //파일 업로드
            .addOnSuccessListener {
                // 화면 종료
                val intent = Intent(this, MainActivity::class.java)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            .addOnFailureListener {
                Log.d("jh", "image upload error ............")

            }
    }


    //교재 저자가 제공하는 이미지 크기 조정 함수
    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            var inputStream = contentResolver.openInputStream(fileUri)

            //inJustDecodeBounds 값을 true 로 설정한 상태에서 decodeXXX() 를 호출.
            //로딩 하고자 하는 이미지의 각종 정보가 options 에 설정 된다.
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //비율 계산........................
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        //inSampleSize 비율 계산
        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}
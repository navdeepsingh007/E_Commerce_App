package com.example.fleet.views.authentication

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.fleet.R
import com.example.fleet.application.MyApplication
import com.example.fleet.callbacks.ChoiceCallBack
import com.example.fleet.common.UtilsFunctions
import com.example.fleet.constants.GlobalConstants
import com.example.fleet.databinding.ActivitySignupBinding
import com.example.fleet.model.LoginResponse
import com.example.fleet.sharedpreference.SharedPrefClass
import com.example.fleet.utils.BaseActivity
import com.example.fleet.utils.DialogClass
import com.example.fleet.utils.Utils
import com.example.fleet.utils.ValidationsClass
import com.example.fleet.viewmodels.RegisterViewModel
import com.example.fleet.views.home.DashboardActivity
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : BaseActivity(), ChoiceCallBack {
    private lateinit var registerViewModel : RegisterViewModel
    private lateinit var binding : ActivitySignupBinding
    private var sharedPrefClass = SharedPrefClass()
    private var confirmationDialog : Dialog? = null
    private var mDialogClass = DialogClass()
    private val RESULT_LOAD_IMAGE = 100
    private val CAMERA_REQUEST = 1888
    private var profileImage = ""
    override fun getLayoutId() : Int {
        return R.layout.activity_signup
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initViews() {
        binding = viewDataBinding as ActivitySignupBinding
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        binding.registerViewModel = registerViewModel


        registerViewModel.loading.observe(
            this,
            Observer<Boolean> { aBoolean->
                if (aBoolean!!) {
                    startProgressDialog()
                } else {
                    stopProgressDialog()
                }
            }
        )

        registerViewModel.isClick().observe(
            this, Observer<String>(function =
            fun(it : String?) {
                val password = binding.etPassword.text.toString()
                val confirm = binding.etConfirmPassword.text.toString()
                val fname = binding.etFirstname.text.toString()
                val lname = binding.etLastname.text.toString()
                val email = binding.etEmail.text.toString()
                val address = binding.etAddress.text.toString()

                when (it) {
                    "btn_register" -> {
                        when {
                            fname.isEmpty() -> showError(
                                binding.etFirstname, getString(R.string.empty) + " " + getString(
                                    R.string.fname
                                )
                            )
                            lname.isEmpty() -> showError(
                                binding.etLastname,
                                getString(R.string.empty) + " " + getString(
                                    R.string.lname
                                )
                            )
                            email.isEmpty() -> showError(
                                binding.etEmail,
                                getString(R.string.empty) + " " + getString(
                                    R.string.email
                                )
                            )
                            !email.matches((ValidationsClass.EMAIL_PATTERN).toRegex()) ->
                                showError(
                                    binding.etEmail,
                                    getString(R.string.invalid) + " " + getString(
                                        R.string.email
                                    )
                                )
                            password.isEmpty() -> showError(
                                binding.etPassword,
                                getString(R.string.empty) + " " + getString(
                                    R.string.enter_new_password
                                )
                            )
                            !Utils(this).isValidPassword(password) -> showError(
                                binding.etPassword,
                                MyApplication.instance.getString(
                                    R.string.regex_message
                                )
                            )
                            confirm.isEmpty() -> showError(
                                binding.etConfirmPassword,
                                getString(R.string.empty) + " " + getString(
                                    R.string.confirm_password
                                )
                            )
                            password != confirm -> showError(
                                binding.etConfirmPassword,
                                MyApplication.instance.getString(R.string.mismatch_paaword)
                            )
                            else -> {
                                val phonenumber = sharedPrefClass.getPrefValue(
                                    MyApplication.instance,
                                    getString(R.string.key_phone)
                                ) as String
                                val countrycode = sharedPrefClass.getPrefValue(
                                    MyApplication.instance,
                                    getString(R.string.key_country_code)
                                ) as String
                                val androidId = UtilsFunctions.getAndroidID()
                                val mHashMap = HashMap<String, RequestBody>()
                                mHashMap["first_name"] = Utils(this).createPartFromString(fname)
                                mHashMap["last_name"] = Utils(this).createPartFromString(lname)
                                mHashMap["user_type"] = Utils(this).createPartFromString("1")
                                mHashMap["phone_number"] = Utils(this).createPartFromString(phonenumber)
                                mHashMap["country_code"] = Utils(this).createPartFromString(countrycode)
                                mHashMap["device_id"] = Utils(this).createPartFromString(androidId)
                                mHashMap["device_type"] = Utils(this).createPartFromString(GlobalConstants.PLATFORM)
                                mHashMap["address"] = Utils(this).createPartFromString(address)
                                mHashMap["gender"] = Utils(this).createPartFromString("1")
                                mHashMap["notify_id"] = Utils(this).createPartFromString(
                                    SharedPrefClass().getPrefValue(
                                        MyApplication.instance,
                                        GlobalConstants.NOTIFICATION_TOKEN
                                    ) as String
                                )
                                mHashMap["email"] = Utils(this).createPartFromString(email)
                                mHashMap["password"] = Utils(this).createPartFromString(password)
                                var userImage : MultipartBody.Part? = null
                                if (!profileImage.isEmpty()) {
                                    val f1 = File(profileImage)
                                    userImage = Utils(this).prepareFilePart("userImage", f1)
                                }


                                registerViewModel.callRegister(mHashMap, userImage)
                            }
                        }

                    }
                    "upload_profile_layer" -> {
                        confirmationDialog = mDialogClass.setUploadConfirmationDialog(this, this, "gallery")

                    }
                }
            })
        )





        registerViewModel.getRegisterResponse().observe(this,
            Observer<LoginResponse> { response->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message!!

                    when {
                        response.code == 200 -> {
                            saveSharedData(response)
                            this.eventCreatedDialog(this, "signup", message)
                            val intent = Intent(this, DashboardActivity::class.java)
                            startActivity(intent)
                        }
                        else -> showToastError(message)
                    }

                }
            })

    }

    private fun showError(textView : TextView, error : String) {
        textView.requestFocus()
        textView.error = error
    }

    private fun saveSharedData(loginResponse : LoginResponse) {
        SharedPrefClass().putObject(
            this,
            GlobalConstants.ACCESS_TOKEN,
            loginResponse.data!!.jwtToken
        )
        SharedPrefClass().putObject(
            this,
            GlobalConstants.USERID,
            loginResponse.data!!.userId
        )

        SharedPrefClass().putObject(
            this, GlobalConstants.USERDATA,
            loginResponse.data!!
        )


        SharedPrefClass().putObject(
            this,
            "isLogin",
            true
        )

    }

    override fun photoFromCamera(mKey : String) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile : File? = try {
                    createImageFile()
                } catch (ex : IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI : Uri = FileProvider.getUriForFile(this, packageName + ".fileprovider", it)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST)
                }
            }
        }
    }

    private fun createImageFile() : File {
        // Create an image file name
        val timeStamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir : File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        //currentPhotoPath = File(baseActivity?.cacheDir, fileName)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            profileImage = absolutePath
        }
    }

    override fun photoFromGallery(mKey : String) {
        val i = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(i, RESULT_LOAD_IMAGE)
    }

    override fun videoFromCamera(mKey : String) {
        //("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun videoFromGallery(mKey : String) {
        //("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            val selectedImage = data.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
            cursor?.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val picturePath = cursor.getString(columnIndex)
            profileImage = picturePath
            setImage(picturePath)
            cursor.close()
        } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK /*&& null != data*/) {
            setImage(profileImage)            // val extras = data!!.extras
            // val imageBitmap = extras!!.get("data") as Bitmap
            //getImageUri(imageBitmap)
        }

    }

    private fun setImage(path : String) {
        Glide.with(this)
            .load(path)
            .placeholder(R.drawable.user)
            .into(binding.uploadIcon)
    }
}

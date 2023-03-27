package com.example.aplikasimonitoringdanevaluasi.ui.main.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.aplikasimonitoringdanevaluasi.R
import com.example.aplikasimonitoringdanevaluasi.databinding.FragmentLoginBinding
import com.example.aplikasimonitoringdanevaluasi.utils.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class LoginFragment : Fragment() {

    companion object {
        private const val TAG = "Login Fragment"
    }

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val args: LoginFragmentArgs by navArgs()
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                if (email.isEmpty()) {
                    etEmail.error("Email belum diisi")
                    etEmail.requestFocus()
                } else if (password.isEmpty()) {
                    etPassword.error("Password belum diisi")
                    etPassword.requestFocus()
                } else {
                    btnLogin.gone()
                    ivGoogleLogin.gone()
                    tvOr.gone()
                    progressBarLogin.visible()
                    progressBarLogin.playAnimation()
                    hideKeyboard()
                    Handler().postDelayed({
                        when (args.role) {
                            getString(R.string.admin) -> {
                                loginViewModel.loginAdminByEmailPassword(email, password)
                                    .observe(viewLifecycleOwner) {
                                        if (it != null) {
                                            loginFirebase()
                                            getInstance(requireContext()).putString(
                                                Constant.ID,
                                                it.id
                                            )
                                            getInstance(requireContext()).putString(
                                                Constant.NAME,
                                                it.name
                                            )
                                            getInstance(requireContext()).putString(
                                                Constant.ROLE,
                                                getString(R.string.admin)
                                            )
                                        } else {
                                            requireContext().showToast("Email atau password salah")
                                            progressBarLogin.gone()
                                            btnLogin.visible()
                                            ivGoogleLogin.visible()
                                            tvOr.visible()
                                        }
                                    }
                            }

                            getString(R.string.company) -> {
                                loginViewModel.loginCompanyByEmailPassword(email, password)
                                    .observe(viewLifecycleOwner) {
                                        if (it != null) {
                                            loginFirebase()
                                            getInstance(requireContext()).putString(
                                                Constant.ID,
                                                it.id
                                            )
                                            getInstance(requireContext()).putString(
                                                Constant.NAME,
                                                it.contactName
                                            )
                                            getInstance(requireContext()).putString(
                                                Constant.ROLE,
                                                getString(R.string.company)
                                            )
                                        } else {
                                            requireContext().showToast("Email atau password salah")
                                            progressBarLogin.gone()
                                            btnLogin.visible()
                                            ivGoogleLogin.visible()
                                            tvOr.visible()
                                        }
                                    }
                            }
                            getString(R.string.student) -> {
                                loginViewModel.loginStudentByEmailPassword(email, password)
                                    .observe(viewLifecycleOwner) {
                                        if (it != null) {
                                            loginFirebase()
                                            getInstance(requireContext()).putString(
                                                Constant.ID,
                                                it.id
                                            )
                                            getInstance(requireContext()).putString(
                                                Constant.NAME,
                                                it.name
                                            )
                                            getInstance(requireContext()).putString(
                                                Constant.ROLE,
                                                getString(R.string.student)
                                            )
                                        } else {
                                            requireContext().showToast("Email atau password salah")
                                            btnLogin.visible()
                                            progressBarLogin.gone()
                                            ivGoogleLogin.visible()
                                            tvOr.visible()
                                        }
                                    }
                            }
                        }
                    }, 1000)

                }
            }

            val gso = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
            auth = FirebaseAuth.getInstance()

            ivGoogleLogin.setOnClickListener {
                login()
            }

            when (args.role) {
                getString(R.string.student) -> tvRegisterLogin.setOnClickListener {
                    val action =
                        LoginFragmentDirections.actionLoginFragmentToRegisterStudentByEmailPassword(
                            args.role,
                            null
                        )
                    findNavController().navigate(action)
                }
                getString(R.string.company) -> tvRegisterLogin.setOnClickListener {
                    val action =
                        LoginFragmentDirections.actionLoginFragmentToRegisterCompanyByEmailPassword(
                            args.role,
                            null
                        )
                    findNavController().navigate(action)
                }

                getString(R.string.admin) -> tvRegisterLogin.setOnClickListener {
                    val action =
                        LoginFragmentDirections.actionLoginFragmentToRegisterAdminByEmailPasswordFragment(
                            args.role,
                            null
                        )
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun loginFirebase() {
        requireContext().showToast("Login berhasil")
        when (args.role) {
            getString(R.string.company) -> findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToHomeCompanyFragment(
                    getString(R.string.company)
                )
            )
            getString(R.string.student) -> findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToHomeStudentFragment(
                    getString(R.string.student)
                )
            )
            getString(R.string.admin) -> findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToHomeAdminFragment(
                    getString(R.string.admin)
                )
            )
        }
    }

    private fun login() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                    Log.d(TAG, "firebaseAuthWithGoogle: " + account.id)
                } catch (e: ApiException) {
                    Log.w(TAG, "Google sign in failed: ", e)
                }
            }
        }

    private fun firebaseAuthWithGoogle(idToken: String) {
        binding.apply {
            btnLogin.gone()
            ivGoogleLogin.gone()
            tvOr.gone()
            progressBarLogin.visible()
            progressBarLogin.playAnimation()
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val emailUser = auth.currentUser?.email
                        updateUI(emailUser.toString())
                        Log.d(TAG, "firebaseAuthWithGoogle: success")
                    } else {
                        Log.w(TAG, "firebaseAuthWithGoogle: ", task.exception)
                    }
                }
        }
    }

    private fun updateUI(email: String) {
        binding.apply {
            when (args.role) {
                getString(R.string.admin) -> {
                    loginViewModel.loginAdminByGoogleAuth(email).observe(viewLifecycleOwner) {
                        if (it != null) {
                            getInstance(requireContext()).putString(Constant.ID, it.id)
                            getInstance(requireContext()).putString(
                                Constant.NAME,
                                it.name
                            )
                            getInstance(requireContext()).putString(
                                Constant.ROLE,
                                getString(R.string.admin)
                            )
                            findNavController().navigate(
                                LoginFragmentDirections.actionLoginFragmentToHomeAdminFragment(
                                    getString(R.string.admin)
                                )
                            )
                        } else {
                            val action =
                                LoginFragmentDirections.actionLoginFragmentToRegisterAdminFragment(
                                    args.role,
                                    email
                                )
                            findNavController().navigate(action)
                        }
                        btnLogin.visible()
                        progressBarLogin.gone()
                    }
                }
                getString(R.string.company) -> {
                    loginViewModel.loginCompanyByGoogleAuth(email).observe(viewLifecycleOwner) {
                        if (it != null) {
                            getInstance(requireContext()).putString(Constant.ID, it.id)
                            getInstance(requireContext()).putString(
                                Constant.NAME,
                                it.contactName
                            )
                            getInstance(requireContext()).putString(
                                Constant.ROLE,
                                getString(R.string.company)
                            )
                            findNavController().navigate(
                                LoginFragmentDirections.actionLoginFragmentToHomeCompanyFragment(
                                    getString(R.string.company)
                                )
                            )
                        } else {
                            val action =
                                LoginFragmentDirections.actionLoginFragmentToRegisterCompanyFragment(
                                    args.role,
                                    email
                                )
                            findNavController().navigate(action)
                        }
                        btnLogin.visible()
                        progressBarLogin.gone()
                    }
                }
                getString(R.string.student) -> {
                    loginViewModel.loginStudentByGoogleAuth(email).observe(viewLifecycleOwner) {
                        if (it != null) {
                            getInstance(requireContext()).putString(Constant.ID, it.id)
                            getInstance(requireContext()).putString(
                                Constant.NAME,
                                it.name
                            )
                            getInstance(requireContext()).putString(
                                Constant.ROLE,
                                getString(R.string.student)
                            )
                            findNavController().navigate(
                                LoginFragmentDirections.actionLoginFragmentToHomeStudentFragment(
                                    getString(R.string.student)
                                )
                            )
                        } else {
                            val action =
                                LoginFragmentDirections.actionLoginFragmentToRegisterStudentFragment(
                                    args.role,
                                    email
                                )
                            findNavController().navigate(action)
                        }
                        btnLogin.visible()
                        progressBarLogin.gone()
                    }
                }
            }
        }
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}
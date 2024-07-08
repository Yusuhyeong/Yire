package com.suhyeong.yire.firebase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class Firestore {

    private val firestore = FirebaseFirestore.getInstance()

    fun checkUidAndNickname(uid: String, callback: (Boolean, Boolean, String?) -> Unit) {
        val userRef = firestore.collection("uid").document(uid)
        userRef.get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val nickname = document.getString("nickname")
                if (nickname != null && nickname.isNotEmpty()) {
                    callback(true, true, nickname)
                } else {
                    callback(true, false, null)
                }
            } else {
                callback(false, false, null)
            }
        }.addOnFailureListener { e ->
            Log.e("YLOG", "Error getting document", e)
            callback(false, false, null)
        }
    }

    fun setNickName(uid: String, nickname: String, callback: (Boolean) -> Unit) {
        val userRef = firestore.collection("uid").document(uid)
        userRef.update("nickname", nickname)
            .addOnSuccessListener {
                Log.d("YLOG", "닉네임 저장 성공!")
                callback(true)
            }
            .addOnFailureListener { e ->
                Log.e("YLOG", "닉네임 저장 실패", e)
                callback(false)
            }
    }

    fun setUid(uid: String, callback: (Boolean) -> Unit) {
        Log.d("YLOG", "uid 추가, 추가 uid : $uid")

        val userRef = firestore.collection("uid").document(uid)
        userRef.set(emptyMap<String, Any>()).addOnSuccessListener {
            Log.d("YLOG", "UID 추가 성공!!")
            callback(true)
        }.addOnFailureListener { e ->
            Log.e("YLOG", "UID 추가 실패!!", e)
            callback(false)
        }
    }
}
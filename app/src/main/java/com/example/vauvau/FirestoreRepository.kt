package com.example.vauvau

import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("oglasi")

    fun dodajOglas(oglas: AnimalAd) {
        val noviDokument = collection.document()
        val oglasSaId = oglas.copy(id = noviDokument.id)
        noviDokument.set(oglasSaId)
    }

    fun dohvatiOglase(onResult: (List<AnimalAd>) -> Unit) {
        collection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                onResult(emptyList())
                return@addSnapshotListener
            }

            val oglasi = snapshot?.documents?.mapNotNull { dokument ->
                dokument.toObject(AnimalAd::class.java)
            } ?: emptyList()

            onResult(oglasi)
        }
    }
}
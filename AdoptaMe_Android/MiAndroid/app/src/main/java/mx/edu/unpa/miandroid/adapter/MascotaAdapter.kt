package mx.edu.unpa.miandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mx.edu.unpa.miandroid.R
import mx.edu.unpa.miandroid.client.RetrofitClient
import mx.edu.unpa.miandroid.model.Mascota

class MascotaAdapter(
    private var listaMascotas: List<Mascota>,
    private val onClick: (Mascota) -> Unit
) : RecyclerView.Adapter<MascotaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNombre: TextView = itemView.findViewById(R.id.txtNombreMascota)
        val txtRaza: TextView = itemView.findViewById(R.id.txtRazaMascota)
        val image: ImageView = itemView.findViewById(R.id.imgMascota)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mascota, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mascota = listaMascotas[position]
        holder.txtNombre.text = mascota.nombre
        holder.txtRaza.text = mascota.raza ?: "Sin raza"

        val primeraImagen = mascota.imagenes?.firstOrNull()

        if (!primeraImagen.isNullOrBlank()) {
            val urlCompleta = if (primeraImagen.startsWith("http")) {
                primeraImagen
            } else {
                RetrofitClient.BASE_URL.trimEnd('/') + "/" + primeraImagen.trimStart('/')
            }

            Glide.with(holder.itemView.context)
                .load(urlCompleta)
                .placeholder(imagenPorDefecto(mascota.tipoMascota))
                .into(holder.image)
        } else {
            holder.image.setImageResource(imagenPorDefecto(mascota.tipoMascota))
        }

        holder.itemView.setOnClickListener {
            onClick(mascota)
        }
    }

    override fun getItemCount(): Int = listaMascotas.size

    fun actualizarLista(nuevaLista: List<Mascota>) {
        listaMascotas = nuevaLista
        notifyDataSetChanged()
    }

    private fun imagenPorDefecto(tipo: String?): Int {
        return when (tipo?.lowercase()) {
            "perro" -> R.drawable.ic_imagen_perrito_foreground
            "gato" -> R.drawable.ic_imagen_gatito_foreground
            "loro" -> R.drawable.ic_imagen_loro_foreground
            "hamster" -> R.drawable.ic_imagen_hamster_foreground
            else -> R.drawable.ic_logo_adoptame_foreground
        }
    }
}
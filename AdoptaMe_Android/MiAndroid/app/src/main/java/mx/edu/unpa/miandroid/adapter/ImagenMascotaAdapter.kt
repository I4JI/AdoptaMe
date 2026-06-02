package mx.edu.unpa.miandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mx.edu.unpa.miandroid.R
import mx.edu.unpa.miandroid.client.RetrofitClient

class ImagenMascotaAdapter(
    private var imagenes: List<String>
) : RecyclerView.Adapter<ImagenMascotaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imgDetalleMascota)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_imagen_mascota, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imagen = imagenes[position]
        val urlCompleta = if (imagen.startsWith("http")) {
            imagen
        } else {
            RetrofitClient.BASE_URL.trimEnd('/') + "/" + imagen.trimStart('/')
        }

        Glide.with(holder.itemView.context)
            .load(urlCompleta)
            .placeholder(R.drawable.ic_logo_adoptame_foreground)
            .into(holder.image)
    }

    override fun getItemCount(): Int = imagenes.size

    fun actualizarLista(nuevasImagenes: List<String>) {
        imagenes = nuevasImagenes
        notifyDataSetChanged()
    }
}
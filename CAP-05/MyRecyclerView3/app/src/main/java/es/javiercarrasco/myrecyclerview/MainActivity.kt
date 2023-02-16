package es.javiercarrasco.myrecyclerview

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import es.javiercarrasco.myrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: RecyclerAdapter
    private var listAnimals: MutableList<MyAnimal> = getAnimals()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
    }

    override fun onStart() {
        super.onStart()

        binding.swipeRefresh.setOnRefreshListener {
            // Se limpia la lista.
            listAnimals.clear()

            // Se vuelven a añadir todos los elementos.
            listAnimals.addAll(getAnimals())

            // Se actualiza el adapter.
            myAdapter.notifyDataSetChanged()

            // Se desactiva el refresco.
            binding.swipeRefresh.isRefreshing = false
        }
    }

    // Método encargado de configurar el RV.
    private fun setUpRecyclerView() {
        // Esta opción a TRUE significa que el RV tendrá
        // hijos del mismo tamaño, optimiza su creación.
        binding.myRVAnimals.setHasFixedSize(true)

        // Se indica el contexto para RV en forma de lista.
        binding.myRVAnimals.layoutManager = LinearLayoutManager(this)

        // Se genera el adapter.
        myAdapter = RecyclerAdapter(listAnimals)

        // Se asigna el adapter al RV.
        binding.myRVAnimals.adapter = myAdapter

//        binding.myRVAnimals.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if (dy > 0)
//                    Log.d("SCROLL", "Down")
//                else if (dy < 0)
//                    Log.d("SCROLL", "Up")
//            }
//        })

        // Activación del Swipe.
        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // Se obtienen las posiciones de inicio y fin.
                val fromPos = viewHolder.adapterPosition
                val toPos = target.adapterPosition

                Log.d("onMove", "Movimiento from $fromPos to $toPos")
                myAdapter.notifyItemMoved(fromPos, toPos)

                return true
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

                if (dX > 0) { // Se comprueba que el itemView se está moviendo.

                    // Se crea el icono a mostrar.
                    val iconTrash: Drawable? =
                        AppCompatResources.getDrawable(applicationContext, R.drawable.delete_sweep)

                    // Se establece el margen izquierdo en base a la altura del itemView y la altura máxima
                    // mediante la cual puede representarse el icono. Al dividirse entre 2, se obtiene el valor
                    // para que se encuentre justo a la misma distancia del margen izquierdo que del margen
                    // superior e inferior.
                    val leftMargin = (viewHolder.itemView.height - iconTrash!!.intrinsicHeight) / 2

                    // Se obtiene la posición de la esquina superior izquierda que tendrá el icono.
                    val iconTopLeftCorner: Int =
                        viewHolder.itemView.top + (viewHolder.itemView.height - iconTrash.intrinsicHeight) / 2

                    // Se obtiene la posición de la esquina inferior izquierda que tendrá el icono.
                    val iconBottomLeftCorner: Int = iconTopLeftCorner + iconTrash.intrinsicHeight

                    // Se obtien el margen izquierdo para el icono.
                    val iconLeftMargin: Int = viewHolder.itemView.left + leftMargin

                    // Se obtien el margen derecho para el icono.
                    val iconRightMargin: Int =
                        viewHolder.itemView.left + leftMargin + iconTrash.intrinsicWidth

                    // Se asignan las medidas al icono en base a los datos obtenidos.
                    iconTrash.setBounds(
                        iconLeftMargin,
                        iconTopLeftCorner,
                        iconRightMargin,
                        iconBottomLeftCorner
                    )

                    // Se crea un color para fondo que se pintará.
                    val fondo = ColorDrawable(Color.RED)

                    // Se establecen las medidas.
                    fondo.setBounds(
                        viewHolder.itemView.left,
                        viewHolder.itemView.top,
                        viewHolder.itemView.left + dX.toInt() + 20, // Para que vaya pintando según se desplaza la X.
                        viewHolder.itemView.bottom
                    )

                    // Se pintan en el canvas por este orden.
                    fondo.draw(c)
                    iconTrash.draw(c)
                }
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Posición del elemento sobre el que se actúa.
                val position = viewHolder.adapterPosition
                // Se almacena el elemento a borrar temporalmente.
                val itemDeleted = listAnimals[position]

                // Se borra y actualiza el adapter.
                listAnimals.removeAt(position)
                myAdapter.notifyItemRemoved(position)

                Snackbar.make(
                    binding.root,
                    "${itemDeleted.animalName} eliminado.",
                    Snackbar.LENGTH_LONG
                ).setAction("Deshacer") {
                    listAnimals.add(position, itemDeleted)
                    myAdapter.notifyItemInserted(position)
                }.show()
            }
        }).attachToRecyclerView(binding.myRVAnimals)

    }

    // Método encargado de generar la fuente de datos.
    private fun getAnimals(): MutableList<MyAnimal> {
        val animals: MutableList<MyAnimal> = arrayListOf()

        animals.add(MyAnimal("Cisne", "Cygnus olor", R.drawable.cisne))
        animals.add(MyAnimal("Erizo", "Erinaceinae", R.drawable.erizo))
        animals.add(MyAnimal("Gato", "Felis catus", R.drawable.gato))
        animals.add(MyAnimal("Gorrión", "Passer domesticus", R.drawable.gorrion))
        animals.add(MyAnimal("Mapache", "Procyon", R.drawable.mapache))
        animals.add(MyAnimal("Oveja", "Ovis aries", R.drawable.oveja))
        animals.add(MyAnimal("Perro", "Canis lupus familiaris", R.drawable.perro))
        animals.add(MyAnimal("Tigre", "Panthera tigris", R.drawable.tigre))
        animals.add(MyAnimal("Zorro", "Vulpes vulpes", R.drawable.zorro))

        return animals
    }
}
import com.example.savemoney.data.dao.GroceryDao
import com.example.savemoney.data.entities.GroceryEntity
import kotlinx.coroutines.flow.Flow

class GroceryRepository(private val groceryDao: GroceryDao) {

    fun fetchAllGroceries(): Flow<List<GroceryEntity>> {
        return groceryDao.fetchAllGroceries()
    }

    suspend fun insert(groceryEntity: GroceryEntity) {
        groceryDao.insert(groceryEntity)
    }

    suspend fun update(groceryEntity: GroceryEntity) {
        groceryDao.update(groceryEntity)
    }

    suspend fun delete(groceryEntity: GroceryEntity) {
        groceryDao.delete(groceryEntity)
    }
}
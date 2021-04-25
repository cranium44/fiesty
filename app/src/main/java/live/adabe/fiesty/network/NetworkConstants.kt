package live.adabe.fiesty.network

object NetworkConstants {
    const val BASE_URL = "http://localhost:3000/api/"
    const val USER_CREATE_ENDPOINT = "createUser"
    const val USER_GET_ENDPOINT = "findUser/:id"
    const val USER_UPDATE_ENDPOINT = "updateUser/:id"
    const val BUILDING_CREATE_ENDPOINT = "/energy/building/:userId"
    const val BUILDING_GETALL_ENDPOINT = "/energy/building/:userId"
    const val BUILDING_GET_ENDPOINT = "/energy/building/:userId/:id"
    const val BUILDING_UPDATE_ENDPOINT = "/energy/building/:userId/:id"
    const val BUILDING_DELETE_ENDPOINT = "/energy/building/:id"
}
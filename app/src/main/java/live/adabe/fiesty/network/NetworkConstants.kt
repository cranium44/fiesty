package live.adabe.fiesty.network

object NetworkConstants {
    const val BASE_URL = "https://home-energy-api.herokuapp.com/api/"
    const val TIMEOUT = 10000L

    //user
    const val USER_CREATE_ENDPOINT = "createUser"
    const val USER_GET_ENDPOINT = "findUser/{id}"
    const val USER_UPDATE_ENDPOINT = "updateUser/{id}"
    const val USER_DELETE_ENDPOINT = "deleteUser/{id}"
    const val USER_LOGIN_ENDPOIT = "loginUser"

    //building
    const val BUILDING_CREATE_ENDPOINT = "energy/building/{userId}"
    const val BUILDING_GETALL_ENDPOINT = "energy/building/{userId}"
    const val BUILDING_GET_ENDPOINT = "energy/building/{userId}/{id}"
    const val BUILDING_UPDATE_ENDPOINT = "energy/building/{userId}/{id}"
    const val BUILDING_DELETE_ENDPOINT = "energy/building/{id}"

    //room
    const val ROOM_CREATE_ENDPOINT = "room/createRoom/{buildingId}"
    const val ROOM_UPDATE_ENDPOINT = "room/createRoom/{roomId}/{buildingId}"
    const val ROOM_GET_ENDPOINT = "room/room/getroom/{roomId}"
    const val ROOM_GETALL_ENDPOINT = "room/room/getAllRooms/{buildingId}"
    const val ROOM_DELETE_ENDPOINT = "room/room/deleteRoom/{roomId}"

    //device
    const val DEVICE_CREATE_ENDPOINT = "appliance/{roomId}"
    const val DEVICE_GETALL_ENDPOINT = "appliances/{roomId}"
    const val DEVICE_GET_ENDPOINT = "appliance/{id}"
    const val DEVICE_UPDATE_ENDPOINT = "appliance/{roomId}/{id}"
    const val DEVICE_DELETE_ENDPOINT = "appliance/{id}"

    //energy
    const val ENERGY_ROOM_ENDPOINT = "energy/{roomId}"
    const val ENERGY_BUILDING_ENDPOINT = "building/{buildingId}"
    const val ENERGY_USER_ENDPOINT = "energy/user/{userId}"
}
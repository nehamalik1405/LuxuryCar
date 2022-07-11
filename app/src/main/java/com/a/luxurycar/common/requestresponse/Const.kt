package com.a.luxurycar.common.requestresponse


class Const {

    companion object {

        const val TUSHAR_TOKEN="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiN2Y4Zjc4OTVkNzhhM2U2MGU3ZDM0ODZiOGNmM2IwZDk0MTY1OGZlNjg5ZjI5MDg1OWE3M2E2YjZmNmJiNDA5YTE3MTM2Njg4MjNjNzM5NWYiLCJpYXQiOjE2NTI2OTk2ODUuMjkzMDg2MDUxOTQwOTE3OTY4NzUsIm5iZiI6MTY1MjY5OTY4NS4yOTMwOTAxMDUwNTY3NjI2OTUzMTI1LCJleHAiOjE2ODQyMzU2ODUuMjkxNTAyOTUyNTc1NjgzNTkzNzUsInN1YiI6IjEzIiwic2NvcGVzIjpbXX0.TAVp1Si_ibIh1B1yFbpU0CMN5Uv37r7yaSh0W-RtIiJ6Fwx4VQyvnoPWpedx-opfKjZ5NHvzZUZmtaoyTeNn95zt_OtWVy46bb9hQNzAx7jT1xgCeT2uIK--ib1ZeII1ItBnUW3kyZm-G9HJnBLzHaJ1073cKixnRBAzDzfM4wN03_rfEACqBXWvbqp27bRsRsC9qeN0nepHhTcUa1mYcZa6J6oqQA_btHUTJ4-1-YdmgxOX8S7SWRrBKQAngNJJXUtTiWLvCej4WRU-gotuBliiuwgzdXWbuT-6K3OptOrKLEY-5fWWkY8GoIAMGsiQaNORV6MtWQzoZQoIxF0DUzO6au19MQtGCa4I4B672kmioBJZAFg7Xjg5H1xrOer8APrwQecn4xOacNKEhI6lrJlY_cmmg1Cm6my8w6O6kDVqOg5P38_5N405LfJPl3CjNfoXchAh7YDTEIWFU3pvIW4cQKPAYsc6hXHCQIKs-alYNhmeCNNFBnMONkoA1y1ewG2j9-Nk_U6RDA3Wuseg2zKe-i-PVq6nMTCGeEX6wVnDt1tlMxvPC3rbAZ75zpBv6HvJWFKhWEzUYz1BKDItDAvg9HYLQ3fn-POf9rxXhgrPv9AloA264oGvbftRtrx8hLhsLqZJVjZJBmTJxCzRXyJfe9qzKKyYxOXuYJJisSU"
      //  const val BASE_URL = "https://onlineprojectprogress.com/laravel/luxary_car/api/" //old url
        const val BASE_URL = "https://onlineprojectprogress.com/laravel/luxury_car_market/api/"
        const val KEY_ID = "id"
        const val KEY_NAME = "name"
        const val KEY_TYPE = "key_type"



        // params for register
        const val PARAM_EMAIL = "email"
        const val PARAM_PASSWARD = "password"
        const val PARAM_CONFIRM_PASSWARD = "password_confirmation"
        const val PARAM_FIRST_NAME = "firstname"
        const val PARAM_LAST_NAME = "lastname"
        const val PARAM_Phone = "phone"
        const val PARAM_COUNTRY = "country_id"
        const val PARAM_STATE = "state_id"
        const val PARAM_CITY = "city_id"

        // params for seller register
        const val SELLER_COMPANY_NAME = "company_name"
        const val SELLER_PARAM_EMAIL = "email"
        const val SELLER_PARAM_PASSWARD = "password"
        const val SELLER_PARAM_CONFIRM_PASSWARD = "password_confirmation"
        const val SELLER_PARAM_FIRST_NAME = "firstname"
        const val SELLER_PARAM_LAST_NAME = "lastname"
        const val SELLER_PARAM_Phone = "phone"
        const val SELLER_PARAM_LOCATION = "location"
        const val SELLER_PARAM_DESCRIPTION = "description"

        // params for login
        const val PARAM_DEVICE_ID = "device_id"
        const val PARAM_DEVICE_TOKEN = "device_token"
        const val PARAM_DEVICE_TYPE = "device_type"

        //params for change password
        const val PARAM_OLD_PASSWARD = "old_password"
        const val PARAM_NEW_PASSWARD = "newpassword"
        const val PARAM_NEW_CONFIRM_PASSWARD = "newpassword_confirmation"

        // param for otp
        const val PARAM_OTP = "otp"
        // gender values
        const val GENDER_MALE = "Male"
        const val GENDER_FEMALE = "Female"
        const val GENDER_OTHERS = "Others"
       const val KEY_BUYER = "Buyer"
        const val KEY_SELLER = "Seller"

      //param for add car
      const val PARAM_CAR_ADS_ID = "car_ads_id"
      const val PARAM_VIN_CHEESIS_NUMBER = "vin"
      const val PARAM_CITY_ID = "city_id"
      const val PARAM_MAKE_ID = "make_id"
      const val PARAM_BODY_TYPE_ID = "body_type_id"
      const val PARAM_CAR_MODEL_ID = "car_model_id"
      const val PARAM_CAR_YEAR = "car_year"
      const val PARAM_RUN_KILOMETERS = "run_kms"
      const val PARAM_PRICE = "price"
      const val PARAM_REGIONAL_SPECIFICATION = "regional_specification"
      const val PARAM_EXTERIOR_COLOR_ID = "exterior_color_id"
      const val PARAM_TRANSMISSION_TYPE = "transmission_type"
      const val PARAM_HORSE_POWER_Id = "horse_power_id"
      const val PARAM_SELLER_TYPE = "seller_type"
      const val PARAM_FULL_SERVICE_HISTORY = "full_service_history"
      const val PARAM_NO_OF_CYLINDERS = "no_of_cylinders"
      const val PARAM_INTERIOR_COLOR_ID = "interior_color_id"
      const val PARAM_FUEL_TYPE = "fuel_type"
      const val PARAM_BODY_CONDITION_ID = "body_condition_id"
      const val PARAM_MECHANICAL_CONDITION_ID = "mechanical_condition_id"
      const val PARAM_STEERING_TYPE = "steering_type"
      const val PARAM_WARRENTY = "warranty"
      const val PARAM_SALES_PERSON_ID = "sales_person_id"
      const val PARAM_TITLE = "title"
      const val PARAM_DESCRIPTION = "description"
      const val PARAM_LOCATION_URL = "location_url"
      const val PARAM_TOUR_URL = "360_tour_url"
      const val PARAM_RENT= "rent"
      const val PARAM_DAILY_RENT_PRICE = "daily_rent_price"
      const val PARAM_WEEKLY_RENT_PRICE = "weekly_rent_price"
      const val PARAM_MONTHLY_RENT_PRICE = "monthly_rent_price"
      const val PARAM_SELLER_ID = "seller_id"





    }

}
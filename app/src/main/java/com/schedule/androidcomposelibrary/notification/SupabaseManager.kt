package com.schedule.androidcomposelibrary.notification

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime

object SupabaseManager {

    private const val SUPABASE_URL = "https://wlhqohqcockkudhqmtvu.supabase.co"
    private const val SUPABASE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndsaHFvaHFjb2Nra3VkaHFtdHZ1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjI5OTM0MDIsImV4cCI6MjA3ODU2OTQwMn0.vmr-AGL0JuM8tmS1sYihjplxqWSaUcmm5a78tiuNTWQ"

    val client: SupabaseClient by lazy {
        createSupabaseClient(
            supabaseUrl = SUPABASE_URL,
            supabaseKey = SUPABASE_KEY
        ) {
            install(Auth)
            install(Postgrest)
            install(Realtime)
        }
    }
}

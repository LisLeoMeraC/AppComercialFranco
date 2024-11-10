package com.example.francoapp

import com.example.francoapp.datos.ClienteRepository
import com.example.francoapp.datos.LoginRepository
import com.example.francoapp.datos.repositorio.ClienteRepositoryImpl
import com.example.francoapp.datos.repositorio.LoginRepositoryImpl
import com.example.francoapp.datos.servicio.ServiciosAPI
import com.example.francoapp.dominio.ClienteUseCase
import com.example.francoapp.dominio.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideLoginRepository(api: ServiciosAPI): LoginRepository {
        return LoginRepositoryImpl(api)
    }

    @Provides
    fun provideLoginUseCase(repository: LoginRepository): LoginUseCase {
        return LoginUseCase(repository)
    }

    @Provides
    fun provideClienteRepository(api: ServiciosAPI): ClienteRepository {
        return ClienteRepositoryImpl(api)
    }

    @Provides
    fun provideClienteUseCase(clienteRepository: ClienteRepository): ClienteUseCase {
        return ClienteUseCase(clienteRepository)
    }
}
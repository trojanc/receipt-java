package coza.trojanc.receipt.loader

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.lang.reflect.ParameterizedType

/**
 * @author Charl Thiem
 */
open class YamlLoader<T> : AbstractLoader<T>(YAMLMapper()) {

    protected var clazz: Class<T>

    init {
        this.clazz = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<T>
    }

    override fun prepareMapper(mapper: ObjectMapper) {
        (mapper as YAMLMapper).disable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID)
    }

    @Throws(IOException::class)
    override fun load(inputStream: InputStream): T {
        return load(inputStream, getMapper(), clazz)
    }

    @Throws(IOException::class)
    override fun load(jsonString: String): T {
        return load(jsonString, getMapper(), clazz)
    }

    @Throws(IOException::class)
    override fun write(instance: T, out: OutputStream) {
        write(instance, out, getMapper())
    }
}
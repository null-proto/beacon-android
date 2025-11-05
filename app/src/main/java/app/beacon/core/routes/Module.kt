package app.beacon.core.routes

import android.content.Context
import app.beacon.core.net.Frame
import app.beacon.core.net.types.Kv

interface Module {
    val name : String

    @OptIn(ExperimentalUnsignedTypes::class)
    class Result(var kv: Kv = Kv(), result: String , code: Int? = null) {

        init {
            kv.put("result" , result)
            if (code!=null) kv.put("returns", code.toString())
        }

        companion object {
            internal fun error(code : Int? = null): Result {
                return Result(result = "error" , code = code)
            }

            internal fun ok(code: Int? = null): Result {
                return Result(result = "ok" , code = code)
            }
        }

        fun with(block: Kv.()->Unit ) {
            kv.block()
        }

        fun put(key: String , value: String) {
            kv.put(key,value)
        }

        fun asFrame() : Frame {
            return Frame.from(kv)
        }
    }
    suspend fun work(args: Args) : Result
}
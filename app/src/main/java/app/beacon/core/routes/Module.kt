package app.beacon.core.routes

import android.content.Context
import app.beacon.core.net.Frame
import app.beacon.core.net.types.Kv
import app.beacon.core.request.C

interface Module {
    val name : String

    @OptIn(ExperimentalUnsignedTypes::class)
    class Result(var kv: Kv = Kv(), result: String , code: Int? = null) {

        init {
            kv.put(C.RESULT , result)
            if (code!=null) kv.put(C.CODE, code.toString())
        }

        companion object {
            internal fun error(code : Int? = null): Result {
                return Result(result = C.ERROR , code = code)
            }

            internal fun ok(code: Int? = null): Result {
                return Result(result = C.OK , code = code)
            }
        }

        fun with(block: Kv.()->Unit ) : Result {
            kv.block()
            return this
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
import com.objectpartners.sbdemo.gamejob.GameItemProcessor
import com.objectpartners.sbdemo.gamejob.GameLineMapper
import com.objectpartners.sbdemo.gamejob.GameReader
import com.objectpartners.sbdemo.gamejob.GameWriter
import com.objectpartners.sbdemo.gamejob.StandingProcessor
import com.objectpartners.sbdemo.gamejob.StandingWriter
import org.springframework.batch.item.file.FlatFileItemReader

beans {
    xmlns batch:"http://www.springframework.org/schema/batch"

    batch.job(id: 'gameJob', restartable: false) {
        batch.step(id: 'step1', next: 'step2') {
            batch.tasklet {
                batch.chunk(
                        reader: 'gameItemReader',
                        processor: 'gameItemProcessor',
                        writer: 'gameWriter',
                        'commit-interval': 5
                )
            }
        }
        batch.step(id: 'step2') {
            batch.tasklet {
                batch.chunk(
                        reader: 'dbGameReader',
                        processor: 'standingsProcessor',
                        writer: 'standingsWriter',
                        'commit-interval': 5
                )
            }
        }
    }

    gameItemReader(FlatFileItemReader) { bean ->
        bean.scope = 'step'
        resource = "#{jobParameters['file']}"
        lineMapper = ref('gameLineMapper')
    }

    gameLineMapper(GameLineMapper)

    gameItemProcessor(GameItemProcessor) { bean ->
        bean.autowire = 'byName'
    }

    gameWriter(GameWriter) { bean ->
        bean.autowire = 'byName'
    }

    dbGameReader(GameReader) { bean ->
        bean.autowire = 'byName'
    }

    standingsProcessor(StandingProcessor) { bean ->
        bean.autowire = 'byName'
    }

    standingsWriter(StandingWriter) { bean ->
        bean.autowire = 'byName'
    }
}
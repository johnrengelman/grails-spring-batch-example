import com.objectpartners.sbdemo.teamjob.TeamReader
import com.objectpartners.sbdemo.teamjob.TeamWriter

beans {
    batch.job(id: 'teamJob', restartable: false) {
        batch.step(id: 'teamStep1') {
            batch.tasklet {
                batch.chunk(
                        reader: 'teamItemReader',
                        writer: 'teamWriter',
                        'commit-interval': 5
                )
            }
        }
    }

    teamItemReader(TeamReader, 'data/teams.csv')

    teamWriter(TeamWriter) { bean ->
        bean.autowire = 'byName'
    }
}
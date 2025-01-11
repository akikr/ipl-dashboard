package com.akikr.ipldashboard.config;

import com.akikr.ipldashboard.model.MatchInput;
import com.akikr.ipldashboard.processor.MatchDataProcessor;
import com.akikr.ipldashboard.repo.Match;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
class BatchJobConfig
{
	private final JobBuilderFactory jobBuilderFactory;
	private final StepBuilderFactory stepBuilderFactory;

	public BatchJobConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory)
	{
		this.jobBuilderFactory = jobBuilderFactory;
		this.stepBuilderFactory = stepBuilderFactory;
	}

	@Value("${match.data.source:match-data.csv}")
	private String matchDataSource;

	private final String[] FIELD_NAMES = new String[] { "id", "city", "date", "player_of_match", "venue", "team1", "team2", "toss_winner",
			"toss_decision", "winner", "result", "result_margin", "method", "umpire1", "umpire2" };

	@Bean
	public FlatFileItemReader<MatchInput> reader()
	{
		//@formatter:off
		BeanWrapperFieldSetMapper<MatchInput> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(MatchInput.class);
		return new FlatFileItemReaderBuilder<MatchInput>()
				.name("MatchItemReader")
				.resource(new ClassPathResource(matchDataSource))
				.linesToSkip(1)		//To skip the match-data.csv file headers
				.delimited()
				.names(FIELD_NAMES)
				.fieldSetMapper(fieldSetMapper)
				.build();
		//@formatter:on
	}

	@Bean
	public JdbcBatchItemWriter<Match> writer(DataSource dataSource)
	{
		//@formatter:off
		String insertQuery = "INSERT INTO match (id, city, date, player_of_match, venue, team1, team2, toss_winner, toss_decision, match_winner, result, result_margin, umpire1, umpire2)"
				+ " VALUES (:id, :city, :date, :playerOfMatch, :venue, :team1, :team2, :tossWinner, :tossDecision, :matchWinner, :result, :resultMargin, :umpire1, :umpire2)";
		return new JdbcBatchItemWriterBuilder<Match>()
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
				.sql(insertQuery)
				.dataSource(dataSource)
				.build();
		//@formatter:on
	}

	@Bean
	public Job importUserJob(JobCompletionListener listener, Step step1)
	{
		//@formatter:off
		return jobBuilderFactory.get("importUserJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step1)
				.end()
				.build();
		//@formatter:on
	}

	@Bean
	public Step step1(JdbcBatchItemWriter<Match> writer, FlatFileItemReader<MatchInput> reader, MatchDataProcessor processor)
	{
		//@formatter:off
		return stepBuilderFactory.get("step1")
				.<MatchInput, Match> chunk(10)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
		//@formatter:on
	}

	@Bean
	public MatchDataProcessor processor()
	{
		return new MatchDataProcessor();
	}
}

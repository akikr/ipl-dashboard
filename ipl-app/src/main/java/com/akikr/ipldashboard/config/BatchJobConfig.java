package com.akikr.ipldashboard.config;

import com.akikr.ipldashboard.model.MatchInput;
import com.akikr.ipldashboard.repo.Match;
import jakarta.persistence.EntityManager;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
class BatchJobConfig
{
	private final EntityManager entityManager;

	public BatchJobConfig(EntityManager entityManager)
	{
		this.entityManager = entityManager;
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
	public MatchDataProcessor processor()
	{
		return new MatchDataProcessor();
	}

	@Bean
	public MatchItemWriter writer()
	{
		return new MatchItemWriter(entityManager);
	}

	@Bean
	public Step step(
			JobRepository jobRepository,
			PlatformTransactionManager transactionManager,
			FlatFileItemReader<MatchInput> reader,
			MatchDataProcessor processor,
			MatchItemWriter writer)
	{
		//@formatter:off
		return new StepBuilder("step", jobRepository)
				.<MatchInput, Match> chunk(10, transactionManager)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
		//@formatter:on
	}

	@Bean
	public Job importDataJob(JobRepository jobRepository, JobCompletionListener listener, Step step)
	{
		//@formatter:off
		return new JobBuilder("importDataJob", jobRepository)
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(step)
				.end()
				.build();
		//@formatter:on
	}
}

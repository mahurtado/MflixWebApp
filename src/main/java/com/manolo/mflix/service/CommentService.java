// In src/main/java/com/manolo/mflix/service/CommentService.java
package com.manolo.mflix.service;

import com.manolo.mflix.model.Comment;
import com.manolo.mflix.model.Movie;
import com.manolo.mflix.model.User;
import com.manolo.mflix.repository.CommentRepository;
import com.manolo.mflix.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class CommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentService.class);
    private final CommentRepository commentRepository;
    private final MovieRepository movieRepository;
    private final CacheService cacheService; 
    private final static String SAMPLE_COMMENTS [] = {"An absolute masterpiece of modern cinema. I was captivated from start to finish.",
"The plot was incredibly predictable and moved at a snail's pace. I was bored to tears.",
"Stunning visuals and a powerful story. This film will stay with me for a long time.",
"I couldn't connect with any of the characters. Their motivations felt completely unbelievable.",
"A hilarious and heartwarming adventure! Perfect for a family movie night.",
"The lead performance was breathtakingly good. Truly Oscar-worthy.",
"A complete waste of a great concept. The execution was terrible and messy.",
"I was on the edge of my seat the entire time. The suspense is masterfully built.",
"The special effects looked cheap and dated. It completely took me out of the experience.",
"A beautiful, subtle film that perfectly captures the human condition. Highly recommended.",
"Finally, a truly original sci-fi movie! The world-building was incredible.",
"The dialogue was so clunky and unnatural. It felt like it was written by a robot.",
"I laughed, I cried, it became my new favorite movie. An instant classic.",
"It tried to be deep and meaningful but just came across as pretentious and confusing.",
"The soundtrack alone is worth the price of admission. Simply amazing.",
"A solid action flick with some truly mind-blowing stunt work. So much fun!",
"I can't believe I wasted two hours on this. Avoid at all costs.",
"The chemistry between the two leads was electric. A perfect romance.",
"The ending was so abrupt and unsatisfying. It ruined the entire film for me.",
"A thought-provoking documentary that everyone needs to see. Important and well-made.",
"All style and no substance. Looked great, but the story was empty.",
"Perfectly paced and incredibly tense. A masterclass in thriller filmmaking.",
"The acting was wooden and uninspired from the entire cast. Not a single good performance.",
"What a charming little film! It put a huge smile on my face.",
"An interesting premise that was completely squandered by a weak script.",
"The attention to historical detail was incredible. A fascinating watch.",
"This movie is pure fun from beginning to end. Don't think, just enjoy the ride.",
"It was just so... bland. Nothing about it was memorable or interesting.",
"A powerful and emotional story that is beautifully told. Bring tissues!",
"The plot twists were so obvious you could see them coming a mile away.",
"This is how you do a blockbuster right. Great action and characters you care about.",
"I felt like the movie couldn't decide what genre it wanted to be. The tone was all over the place.",
"A genuinely terrifying horror film that relies on atmosphere instead of cheap jump scares.",
"The animation was absolutely gorgeous. Every frame was a work of art.",
"Far too long and self-indulgent. It could have been 45 minutes shorter.",
"An clever and witty script that kept me engaged the whole time.",
"I didn't laugh once during this supposed 'comedy'. The jokes were terrible.",
"A complex and rewarding film that requires your full attention. Worth it.",
"This sequel completely undoes everything that made the first film great. What a disappointment.",
"A simple story, told with immense heart and sincerity. I loved it.",
"The movie treats its audience like idiots, explaining every single plot point.",
"The villain was more compelling than the hero. An unforgettable antagonist.",
"I'm still not entirely sure what happened. The plot was a convoluted mess.",
"It's a slow burn, but the payoff at the end is absolutely worth the wait.",
"Relied way too much on tired clichés. I've seen this exact story a dozen times before.",
"The perfect feel-good movie. It's impossible to watch without smiling.",
"A disappointing adaptation of a great book. It missed the entire point of the source material.",
"Bold, ambitious, and unlike anything I've ever seen before. A true original.",
"The pacing was a disaster. The first hour was interesting, but the rest was a slog.",
"An underrated gem that deserves a much bigger audience. Go see it!"};
    private final static Random RANDOM = new Random();

    @Autowired
    public CommentService(CommentRepository commentRepository, MovieRepository movieRepository, CacheService cacheService) {
        this.commentRepository = commentRepository;
        this.movieRepository = movieRepository;
        this.cacheService = cacheService; 
    }

    /**
     * Creates and saves a random comment using users and movies from the in-memory cache.
     */
    public Optional<Comment> createRandomComment() {
        // 1. Get a random user from the cache
        Optional<User> userOptional = cacheService.getRandomUser();
        if (userOptional.isEmpty()) {
            LOGGER.error("Could not create random comment because the user cache is empty.");
            return Optional.empty();
        }
        User randomUser = userOptional.get();

        // 2. Get a random movie ID from the cache
        Optional<String> movieIdOptional = cacheService.getRandomMovieId();
        if (movieIdOptional.isEmpty()) {
            LOGGER.error("Could not create random comment because the movie ID cache is empty.");
            return Optional.empty();
        }
        String randomMovieId = movieIdOptional.get();
        
        // 3. Fetch the full movie object using the ID
        Optional<Movie> movieOptional = movieRepository.findById(randomMovieId);
        if(movieOptional.isEmpty()){
            // This can happen if a movie was deleted after the cache was loaded
            LOGGER.error("Could not find movie with cached ID: {}. It may have been deleted.", randomMovieId);
            return Optional.empty();
        }
        Movie randomMovie = movieOptional.get();

        // 4. Create and save the new comment
        Comment comment = new Comment();
        comment.setName(randomUser.getName());
        comment.setEmail(randomUser.getEmail());
        comment.setMovieId(randomMovie.getId());
        comment.setText(getRandomComment());
        comment.setDate(new Date());

        Comment savedComment = commentRepository.save(comment);
        LOGGER.info("Successfully created a random comment by '{}' on movie '{}' using cache.", randomUser.getName(), randomMovie.getTitle());

        return Optional.of(savedComment);
    }

    private static String getRandomComment() {
        int randomIndex = RANDOM.nextInt(SAMPLE_COMMENTS.length);
        return SAMPLE_COMMENTS[randomIndex];
    }
}
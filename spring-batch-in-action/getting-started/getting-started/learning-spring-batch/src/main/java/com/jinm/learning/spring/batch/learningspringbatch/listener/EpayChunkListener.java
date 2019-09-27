package com.jinm.learning.spring.batch.learningspringbatch.listener;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * .
 * Created by jinm on  2019/09/26.  contact: keanemer.gmail.com
 */

public class EpayChunkListener {

    @BeforeChunk
    public void beforeChunk(ChunkContext context) {
        System.out.println(context.getStepContext().getStepName()+"chunk before running.....");
    }

    @AfterChunk
    public void afterChunk(ChunkContext context) {
        System.out.println(context.getStepContext().getStepName()+"chunk after running.....");
    }

}

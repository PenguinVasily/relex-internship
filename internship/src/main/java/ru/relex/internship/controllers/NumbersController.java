package ru.relex.internship.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.relex.internship.models.*;
import ru.relex.internship.services.NumberService;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/numbers")

public class NumbersController {
    @Autowired
    private NumberService numberService;

    @GetMapping(value = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public Result getResultFromOperation(@RequestBody Request request) throws Exception {
        return numberService.executeOperation(request.getOperation(), request.getPath());
    }

    @GetMapping(value = "/increasing-sequence",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ListResult getIncreasingSequence(@RequestBody Request request) throws IOException {
        List result = numberService.getIncreasingSequence(request.getPath());
        return new ListResult(result);
    }

    @GetMapping(value = "/decreasing-sequence",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ListResult getDecreasingSequence(@RequestBody Request request) throws IOException {
        List result = numberService.getDecreasingSequence(request.getPath());
        return new ListResult(result);
    }

    @GetMapping(value = "/max",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public IntegerResult getMax(@RequestBody Request request) throws IOException {
        Integer result = numberService.getMax(request.getPath());
        return new IntegerResult(result);
    }

    @GetMapping(value = "/min",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public IntegerResult getMin(@RequestBody Request request) throws IOException {
        Integer result = numberService.getMin(request.getPath());
        return new IntegerResult(result);
    }

    @GetMapping(value = "/median",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public FloatResult getMedian(@RequestBody Request request) throws IOException {
        Float result = numberService.getMedian(request.getPath());
        return new FloatResult(result);
    }

    @GetMapping(value = "/arithmetic-mean",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public FloatResult getArithmeticMean(@RequestBody Request request) throws IOException {
        Float result = numberService.getArithmeticMean(request.getPath());
        return new FloatResult(result);
    }
}

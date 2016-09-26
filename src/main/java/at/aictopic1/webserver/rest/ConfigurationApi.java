package at.aictopic1.webserver.rest;

import at.aictopic1.twitter.TwitterConfiguration;
import at.aictopic1.webserver.Helper;
import at.aictopic1.webserver.service.ITwitterService;
import at.aictopic1.webserver.service.impl.TwitterService;
import org.eclipse.jetty.http.HttpTester;
import twitter4j.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.net.URLDecoder;
import java.text.ParseException;

/**
 * 
 */
@Path("/config")
public class ConfigurationApi {
    private ITwitterService twitterService = TwitterService.getInstance();

    @GET
    public Response getConfig(){
        String output = new JSONObject(twitterService.getConfiguration()).toString();
        return Response.status(200).entity(output).build();
    }

    @PUT
    public Response config(String params){
        String jFile = Helper.getParameter(params, "jsonFile");
        String language = Helper.getParameter(params, "language");
        String algorithm = Helper.getParameter(params, "algorithm");
        String ignoreAccounts = Helper.getParameter(params, "ignoreAccounts");
        String neutral = Helper.getParameter(params, "neutral");
        String normalizeDocLength = Helper.getParameter(params, "normalizeDocLength");
        String newWordsToKeep = Helper.getParameter(params, "newWordsToKeep");
        String outputWordCounts = Helper.getParameter(params, "outputWordCounts");
        String tfidfTransform = Helper.getParameter(params, "tfidfTransform");
        String useDistribution = Helper.getParameter(params, "classifier_useDistribution");

        String bayes_useKernelEstimator = Helper.getParameter(params, "bayes_useKernelEstimator");
        String bayes_useSupervisedDiscretization = Helper.getParameter(params, "bayes_useSupervisedDiscretization");

        String j48_binarySplits = Helper.getParameter(params, "j48_binarySplits");
        String j48_confidenceFactor = Helper.getParameter(params, "j48_confidenceFactor");
        String j48_minNumObj = Helper.getParameter(params, "j48_minNumObj");
        String j48_numFolds = Helper.getParameter(params, "j48_numFolds");
        String j48_errorPruning = Helper.getParameter(params, "j48_errorPruning");
        String j48_saveInstanceData = Helper.getParameter(params, "j48_saveInstanceData");
        String j48_newSeed = Helper.getParameter(params, "j48_newSeed");
        String j48_unpruned = Helper.getParameter(params, "j48_unpruned");
        String j48_useLaplace = Helper.getParameter(params, "j48_useLaplace");

        String rf_maxDepth = Helper.getParameter(params, "rf_maxDepth");
        String rf_newNumFeatures = Helper.getParameter(params, "rf_newNumFeatures");
        String rf_newNumTrees = Helper.getParameter(params, "rf_newNumTrees");
        String rf_seed = Helper.getParameter(params, "rf_seed");

        String kN_newCrossValidate = Helper.getParameter(params, "kN_newCrossValidate");
        String kN_k = Helper.getParameter(params, "kN_k");
        String kN_newMeanSquared = Helper.getParameter(params, "kN_newMeanSquared");
        String kN_newWindowSize = Helper.getParameter(params, "kN_newWindowSize");

        /*if(!jFile.startsWith("data")){
            jFile = "data/"+jFile;
        }*/

        TwitterConfiguration config = new TwitterConfiguration();
        config.setAlgorithm(algorithm);
        config.setJsonFile(jFile);
        config.setLanguage(language);
        config.setIgnoreAccounts(ignoreAccounts);
        config.setClassifier_neutral(neutral != null);
        config.setClassifier_normalizeDocLength(normalizeDocLength != null);
        config.setClassifier_newWordsToKeep(Integer.parseInt(newWordsToKeep));
        config.setClassifier_outputWordCounts(outputWordCounts != null);
        config.setClassifier_tfidfTransform(tfidfTransform != null);
        config.setClassifier_useDistribution(useDistribution != null);

        config.setBayes_useKernelEstimator(bayes_useKernelEstimator != null);
        config.setBayes_useSupervisedDiscretization(bayes_useSupervisedDiscretization != null);

        config.setJ48_binarySplits(j48_binarySplits != null);
        try{ config.setJ48_confidenceFactor(Float.parseFloat(j48_confidenceFactor)); }catch(Exception e) { }
        try{ config.setJ48_minNumObj(Integer.parseInt(j48_minNumObj)); }catch(Exception e) { }
        try{ config.setJ48_numFolds(Integer.parseInt(j48_numFolds)); }catch(Exception e) { }
        config.setJ48_errorPruning(j48_errorPruning != null);
        config.setJ48_saveInstanceData(j48_saveInstanceData != null);
        try{ config.setJ48_newSeed(Integer.parseInt(j48_newSeed)); }catch(Exception e) { }
        config.setJ48_unpruned(j48_unpruned != null);
        config.setJ48_useLaplace(j48_useLaplace != null);

        try{ config.setRf_maxDepth(Integer.parseInt(rf_maxDepth)); }catch(Exception e) { }
        try{ config.setRf_newNumFeatures(Integer.parseInt(rf_newNumFeatures)); }catch(Exception e) { }
        try{ config.setRf_newNumTrees(Integer.parseInt(rf_newNumTrees)); }catch(Exception e) { }
        try{ config.setRf_seed(Integer.parseInt(rf_seed)); }catch(Exception e) { }

        config.setkN_newCrossValidate(kN_newCrossValidate != null);
        try{ config.setkN_k(Integer.parseInt(kN_k)); }catch(Exception e) { }
        config.setkN_newMeanSquared(kN_newMeanSquared != null);
        try{ config.setkN_newWindowSize(Integer.parseInt(kN_newWindowSize)); }catch(Exception e) { }

        twitterService.setConfig(config);

        String output = "Configuration updated";
        return Response.status(200).entity(output).build();
    }
}

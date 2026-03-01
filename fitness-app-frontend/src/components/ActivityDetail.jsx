import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router";
import {
    Box,
    Card,
    CardContent,
    Divider,
    Typography,
    CircularProgress,
    Alert,
} from "@mui/material";
import { List, ListItem, ListItemText } from "@mui/material";
import { Button, Chip } from "@mui/material";
import {
    getActivityById,
    getRecommendationByActivityId
} from "../services/api";



const ActivityDetail = () => {
    const { id } = useParams();
    const [activity, setActivity] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();



    // useEffect(() => {
    //     const fetchActivityDetail = async () => {
    //         setLoading(true);
    //         setError(null);


    //         try {
    //             // const activityResponse = await getActivityById(id);
    //             // const recommendationResponse = await getRecommendationByActivityId(id);

    //             const activityResponse = await getActivityById(id);
    //             let recommendationData = null;
    //             try {
    //                 recommendationData = await getRecommendationByActivityId(id);
    //             } catch (recError) {

    //                 if (recError.response?.status === 404) {
    //                     console.log("Recommendation not ready yet");
    //                     recommendationData = null;
    //                 } else {
    //                     throw recError;
    //                 }
    //             }

    //             console.log("Activity Response:", activityResponse.data);
    //             // console.log("Recommendation Response:", recommendationResponse);
    //             console.log("Recommendation Data:", recommendationData);

    //             // setActivity({
    //             //     ...activityResponse.data,
    //             //     ...recommendationResponse.data
    //             // });

    //             // setActivity({
    //             //     ...activityResponse.data,
    //             //     recommendation: recommendationResponse?.recommendation || null,
    //             //     improvements: recommendationResponse?.improvements || [],
    //             //     suggestions: recommendationResponse?.suggestions || [],
    //             //     safety: recommendationResponse?.safety || [],
    //             // });

    //             setActivity({
    //                 ...activityResponse.data,
    //                 recommendation: recommendationData?.recommendation || null,
    //                 improvements: recommendationData?.improvements || [],
    //                 suggestions: recommendationData?.suggestions || [],
    //                 safety: recommendationData?.safety || [],
    //             });



    //         } catch (err) {
    //             setError("Failed to load activity details");
    //             console.error(err);
    //         } finally {
    //             setLoading(false);
    //         }
    //     };

    //     fetchActivityDetail();
    // }, [id]);

    useEffect(() => {

        let interval;

        const fetchActivityDetail = async () => {

            try {

                const activityResponse = await getActivityById(id);

                let recommendationData = null;

                try {

                    recommendationData =
                        await getRecommendationByActivityId(id);

                } catch (recError) {

                    if (recError.response?.status !== 404) {
                        throw recError;
                    }
                }

                setActivity({
                    ...activityResponse.data,
                    recommendation: recommendationData?.recommendation || null,
                    improvements: recommendationData?.improvements || [],
                    suggestions: recommendationData?.suggestions || [],
                    safety: recommendationData?.safety || [],
                });

                setLoading(false);

                // STOP polling if recommendation exists
                if (recommendationData?.recommendation) {
                    clearInterval(interval);
                }

            } catch (err) {

                setError("Failed to load activity details");
                setLoading(false);
            }
        };

        fetchActivityDetail();

        // poll every 5 seconds
        interval = setInterval(fetchActivityDetail, 5000);

        return () => clearInterval(interval);

    }, [id]);

    if (loading) {
        return (
            <Box sx={{ display: "flex", justifyContent: "center", mt: 4 }}>
                <CircularProgress />
            </Box>
        );
    }

    if (error) {
        return <Alert severity="error">{error}</Alert>;
    }

    if (!activity) return null;

    return (
        <Box sx={{ maxWidth: 800, mx: "auto", p: 2 }}>

            <Button
                variant="outlined"
                sx={{ mb: 2 }}
                onClick={() => navigate(-1)}
            >
                ← Back to Activities
            </Button>

            {/* Activity Info Card */}
            <Card sx={{ mb: 3 }}>
                <CardContent>
                    <Typography variant="h5" gutterBottom>
                        Activity Details
                    </Typography>

                    <Typography>
                        <strong>Type:</strong> {activity.activityType}
                    </Typography>

                    <Typography>
                        <strong>Duration:</strong> {activity.duration} minutes
                    </Typography>

                    <Typography>
                        <strong>Calories Burned:</strong> {activity.caloriesBurned}
                    </Typography>

                    <Typography>
                        <strong>Date:</strong>{" "}
                        {activity.startTime
                            ? new Date(activity.startTime).toLocaleString()
                            : "No date available"}
                    </Typography>
                </CardContent>
            </Card>

            {!activity.recommendation && (
                <Card sx={{ backgroundColor: "#fff3cd" }}>
                    <CardContent>
                        <Typography>
                            🤖 Generating AI recommendation... Please wait.
                        </Typography>
                    </CardContent>
                </Card>
            )}

            {/* AI Recommendation Card */}
            {activity.recommendation && (
                <Card sx={{ backgroundColor: "#f9f9f9" }}>
                    <CardContent>
                        <Typography variant="h5" gutterBottom>
                            🤖 AI Recommendation
                        </Typography>

                        <Chip
                            label="AI Generated"
                            color="secondary"
                            size="small"
                        />

                        {/* Analysis */}
                        <Typography variant="h6" sx={{ mt: 2 }}>
                            Analysis
                        </Typography>
                        <Typography component="p" sx={{ mb: 1 }}>
                            {activity.recommendation}
                        </Typography>

                        {/* Improvements */}
                        {activity.improvements?.length > 0 && (
                            <>
                                <Divider sx={{ my: 2 }} />
                                <Typography variant="h6">Improvements</Typography>
                                {activity.improvements.map((improvement, index) => (
                                    <Typography key={index} component="p" sx={{ mb: 1 }}>
                                        • {improvement}
                                    </Typography>
                                ))}
                            </>
                        )}

                        {/* Suggestions */}
                        {activity.suggestions?.length > 0 && (
                            <>
                                <Divider sx={{ my: 2 }} />
                                <Typography variant="h6">Suggestions</Typography>
                                {activity.suggestions.map((suggestion, index) => (
                                    <Typography key={index} component="p" sx={{ mb: 1 }}>
                                        • {suggestion}
                                    </Typography>
                                ))}
                            </>
                        )}

                        {/* Safety */}
                        {activity.safety?.length > 0 && (
                            <>
                                <Divider sx={{ my: 2 }} />
                                <Typography variant="h6">Safety Guidelines</Typography>
                                {activity.safety.map((safety, index) => (
                                    <Typography key={index} component="p" sx={{ mb: 1 }}>
                                        • {safety}
                                    </Typography>
                                ))}
                            </>
                        )}
                    </CardContent>
                </Card>
            )}
        </Box>
    );
};

export default ActivityDetail;

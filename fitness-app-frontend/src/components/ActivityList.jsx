import React, { useEffect, useState } from "react";
import {
    Card,
    CardContent,
    Grid,
    Typography,
    CircularProgress,
    Alert,
    Box,
    Skeleton,
    IconButton,
} from "@mui/material";
import { useNavigate } from "react-router";
import { getActivities, deleteActivity } from "../services/api";
import DeleteIcon from "@mui/icons-material/Delete";

const ActivityList = ({ refresh }) => {
    const [activities, setActivities] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [deletingId, setDeletingId] = useState(null); 
    const navigate = useNavigate();

    useEffect(() => {
        const fetchActivities = async () => {
            try {
                setLoading(true);
                setError(null);

                const response = await getActivities();
                setActivities(response?.data || []);
            } catch (err) {
                console.error(err);
                setError("Failed to load activities");
            } finally {
                setLoading(false);
            }
        };

        fetchActivities();
    }, [refresh]);

    if (loading) {
        return (
            <Grid container spacing={2}>
                {[1, 2, 3].map((item) => (
                    <Grid item xs={12} sm={6} md={4} key={item}>
                        <Skeleton variant="rectangular" height={120} />
                    </Grid>
                ))}
            </Grid>
        );
    }

    if (error) {
        return (
            <Alert severity="error" sx={{ mt: 3 }}>
                {error}
            </Alert>
        );
    }

    if (activities.length === 0) {
        return (
            <Box sx={{ textAlign: "center", mt: 5 }}>
                <Typography variant="h6">No activities yet</Typography>
                <Typography color="text.secondary">
                    Start by adding your first workout!
                </Typography>
            </Box>
        );
    }

    return (
        <Grid container spacing={2}>
            {activities.map((activity) => (
                <Grid item xs={12} sm={6} md={4} key={activity.id} sx={{ position: "relative" }}>
                    <Card
                        sx={{
                            cursor: "pointer",
                            transition: "0.3s",
                            "&:hover": {
                                boxShadow: 6,
                                transform: "translateY(-4px)",
                            },
                        }}
                        onClick={() => navigate(`/activities/${activity.id}`)}
                    >
                        <CardContent>
                            <Typography variant="h6" gutterBottom>
                                {activity.activityType}
                            </Typography>

                            <Typography variant="body2">
                                Duration: {activity.duration} mins
                            </Typography>

                            <Typography variant="body2">
                                Calories: {activity.caloriesBurned} kcal
                            </Typography>

                            <IconButton
                                edge="end"
                                color="error"
                                sx={{ position: "absolute", top: 8, right: 8 }}
                                onClick={async (e) => {
                                    e.stopPropagation(); // Prevent card click

                                    if (!window.confirm("Are you sure you want to delete this activity?")) return;

                                    try {
                                        setDeletingId(activity.id); // Show spinner
                                        await deleteActivity(activity.id);
                                        setActivities((prev) =>
                                            prev.filter((a) => a.id !== activity.id)
                                        );
                                    } catch (err) {
                                        console.error(err);
                                        alert("Failed to delete activity");
                                    } finally {
                                        setDeletingId(null);
                                    }
                                }}
                            >
                                {deletingId === activity.id ? (
                                    <CircularProgress size={24} />
                                ) : (
                                    <DeleteIcon />
                                )}
                            </IconButton>
                        </CardContent>
                    </Card>
                </Grid>
            ))}
        </Grid>
    );
};

export default ActivityList;

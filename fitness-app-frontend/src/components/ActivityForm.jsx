import {
    Box,
    Button,
    FormControl,
    InputLabel,
    MenuItem,
    Select,
    TextField,
} from "@mui/material";
import React, { useState } from "react";
import { addActivity } from "../services/api";

const initialState = {
    activityType: "RUNNING",
    duration: "",
    caloriesBurned: "",
    startTime: null,   // optional
    additionalMetrics: {},
};


const ActivityForm = ({ onActivityAdded }) => {
    const [activity, setActivity] = useState(initialState);
    const [loading, setLoading] = useState(false);

    const handleChange = (field) => (e) => {
        setActivity({
            ...activity,
            [field]:
                field === "duration" || field === "caloriesBurned"
                    ? Number(e.target.value)
                    : e.target.value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (activity.duration <= 0 || activity.caloriesBurned <= 0) {
            alert("Duration and calories must be greater than 0");
            return;
        }

        const payload = {
            ...activity,
            startTime: new Date().toISOString(), 
        };

        setLoading(true);
        try {
            await addActivity(payload);
            onActivityAdded();
            setActivity(initialState);
        } catch (error) {
            console.error(error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <Box component="form" onSubmit={handleSubmit} sx={{ mb: 4 }}>
            <FormControl fullWidth sx={{ mb: 2 }}>
                <InputLabel>Activity Type</InputLabel>
                <Select
                    value={activity.activityType}
                    onChange={handleChange("activityType")}
                >

                    <MenuItem value="RUNNING">Running</MenuItem>
                    <MenuItem value="WALKING">Walking</MenuItem>
                    <MenuItem value="CYCLING">Cycling</MenuItem>
                    <MenuItem value="SWIMMING">Swimming</MenuItem>
                    <MenuItem value="WEIGHT_TRAINING">Weight Training</MenuItem>
                    <MenuItem value="YOGA">Yoga</MenuItem>
                    <MenuItem value="HIIT">HIIT</MenuItem>
                    <MenuItem value="CARDIO">Cardio</MenuItem>
                    <MenuItem value="STRETCHING">Stretching</MenuItem>
                    <MenuItem value="OTHER">Other</MenuItem>
                </Select>
            </FormControl>

            <TextField
                fullWidth
                label="Duration (Minutes)"
                type="number"
                sx={{ mb: 2 }}
                value={activity.duration}
                onChange={handleChange("duration")}
            />

            <TextField
                fullWidth
                label="Calories Burned"
                type="number"
                sx={{ mb: 2 }}
                value={activity.caloriesBurned}
                onChange={handleChange("caloriesBurned")}
            />

            <Button type="submit" variant="contained" disabled={loading}>
                {loading ? "Adding..." : "Add Activity"}
            </Button>
        </Box>
    );
};

export default ActivityForm;

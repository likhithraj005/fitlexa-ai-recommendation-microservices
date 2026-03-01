import axios from "axios";



// Use environment variable for the backend URL
const API_URL = import.meta.env.VITE_BACKEND_URL + '/api';

console.log("API URL:", API_URL); // Debugging line to check the API URL

const api = axios.create({
    baseURL: API_URL
});

api.interceptors.request.use((config) => {
    const userId = localStorage.getItem('userId');
    const token = localStorage.getItem('token');

    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
    }

    if (userId) {
        config.headers['X-User-ID'] = userId;
    }
    return config;
}
);


export const getActivities = () => api.get('/activities');

export const addActivity = (activity) => api.post('/activities', activity);

export const deleteActivity = (id) => api.delete(`/activities/${id}`);

// export const getActivityDetail = (id) => api.get(`/recommendations/activity/${id}`);
export const getActivityById = (id) => api.get(`/activities/${id}`);

//export const getRecommendationByActivityId = (id) => api.get(`/recommendations/activity/${id}`);
export const getRecommendationByActivityId = async (id) => {
    try {
        const response = await api.get(`/recommendations/activity/${id}`);
        return response.data;
    } catch (err) {
        if (err.response?.status === 404) {
            // Return a placeholder if recommendation doesn't exist yet
            return {
                activityId: id,
                message: "Recommendation is being generated..."
            };
        } else {
            console.error("Error fetching recommendation:", err);
            throw err;
        }
    }
};


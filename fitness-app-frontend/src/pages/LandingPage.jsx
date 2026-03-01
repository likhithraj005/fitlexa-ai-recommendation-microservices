import { Box, Button, Typography, Container, Grid, Card } from "@mui/material";
import BarChartIcon from "@mui/icons-material/BarChart";
import PsychologyIcon from "@mui/icons-material/Psychology";
import TrendingUpIcon from "@mui/icons-material/TrendingUp";
import geminiImg from "../assets/geminiqna.png";
import draftwiseImg from "../assets/draftwise.png";
import yumecraftImg from "../assets/yumecraft.png";
import LinkedInIcon from "@mui/icons-material/LinkedIn";
import GitHubIcon from "@mui/icons-material/GitHub";
import InstagramIcon from "@mui/icons-material/Instagram";
import LanguageIcon from "@mui/icons-material/Language";




const LandingPage = ({ logIn }) => {
    return (
        <>
            {/* Header */}
            <Box
                sx={{
                    display: "flex",
                    justifyContent: "space-between",
                    alignItems: "center",
                    px: 4,
                    py: 2,
                    borderBottom: "1px solid #eee",
                }}
            >
                {/* <Typography variant="h6">
                    FitNova - Your Personal Fitness Companion
                </Typography> */}

                {/* Left Side (Logo + Subtitle) */}
                <Box
                    sx={{
                        display: "flex",
                        alignItems: "center",
                        gap: 2,
                    }}
                >
                    <Typography
                        variant="h4"
                        sx={{
                            fontWeight: "bold",
                            background: "linear-gradient(90deg, #8E2DE2, #4A00E0, #1E90FF)",
                            WebkitBackgroundClip: "text",
                            WebkitTextFillColor: "transparent",
                        }}
                    >
                        Fitlexa
                    </Typography>

                    <Typography
                        variant="body1"
                        sx={{ color: "text.secondary" }}
                    >
                        Your Personal Fitness Companion
                    </Typography>
                </Box>

                <Button variant="contained" onClick={() => {
                    console.log("Login clicked");
                    logIn();
                }}>
                    Login
                </Button>


            </Box>

            {/* Hero Section */}
            <Container maxWidth="md">
                <Box sx={{ textAlign: "center", py: 10 }}>
                    <Typography variant="h4" fontWeight="bold" gutterBottom>
                        Transform Your Fitness Journey with AI
                    </Typography>

                    <Typography variant="h6" color="text.secondary" sx={{ mb: 4 }}>
                        Track workouts, monitor progress and get AI-powered
                        recommendations tailored to you.
                    </Typography>

                    <Button
                        variant="contained"
                        size="large"
                        onClick={() => logIn()}
                    >
                        Get Started
                    </Button>
                </Box>
            </Container>

            {/* Features Section */}
            <Container maxWidth="lg">
                <Box sx={{ py: 8 }}>
                    <Typography variant="h4" fontWeight="bold" textAlign="center" gutterBottom>
                        Where AI Meets Smarter Fitness
                    </Typography>

                    <Grid container spacing={4} sx={{ mt: 3 }}>
                        <Grid item xs={12} md={4}>
                            <Typography
                                variant="h6"
                                sx={{
                                    display: "flex",
                                    alignItems: "center",
                                    gap: 1,
                                    justifyContent: "center"
                                }}
                            >
                                <BarChartIcon color="primary" />
                                Smart Tracking
                            </Typography>

                            <Typography color="text.secondary">
                                Log workouts and track progress easily.
                            </Typography>
                        </Grid>

                        <Grid size={{ xs: 12, md: 4 }}>
                            <Typography
                                variant="h6"
                                sx={{
                                    display: "flex",
                                    alignItems: "center",
                                    gap: 1,
                                    justifyContent: "center"
                                }}
                            >
                                <PsychologyIcon color="primary" />
                                AI Insights
                            </Typography>

                            <Typography color="text.secondary" textAlign="center">
                                Get personalized fitness recommendations.
                            </Typography>
                        </Grid>

                        <Grid size={{ xs: 12, md: 4 }}>
                            <Typography
                                variant="h6"
                                sx={{
                                    display: "flex",
                                    alignItems: "center",
                                    gap: 1,
                                    justifyContent: "center"
                                }}
                            >
                                <TrendingUpIcon color="primary" />
                                Progress Analytics
                            </Typography>

                            <Typography color="text.secondary" textAlign="center">
                                Visualize improvements with detailed analytics.
                            </Typography>
                        </Grid>

                    </Grid>
                </Box>
            </Container>

            {/* AI Projects Section */}
            <Box sx={{ py: 10, bgcolor: "#f9fafb" }}>
                <Container maxWidth="lg">
                    <Typography
                        variant="h4"
                        fontWeight="bold"
                        textAlign="center"
                        gutterBottom
                    >
                        Explore More AI Projects
                    </Typography>

                    <Typography
                        variant="body1"
                        color="text.secondary"
                        textAlign="center"
                        sx={{ mb: 6 }}
                    >
                        AI-driven applications built to make everyday tasks faster, easier, and with real-world problem solving.
                    </Typography>

                    <Grid container spacing={4}>
                        {/* Gemini QnA */}
                        <Grid size={{ xs: 12, md: 4 }}>
                            <Card
                                sx={{
                                    p: 4,
                                    textAlign: "center",
                                    borderRadius: 3,
                                    transition: "0.3s",
                                    "&:hover": {
                                        transform: "translateY(-8px)",
                                        boxShadow: 6,
                                    },
                                }}
                            >
                                {/* <PsychologyIcon sx={{ fontSize: 50, mb: 2 }} color="primary" /> */}
                                <Box
                                    component="img"
                                    src={geminiImg}
                                    alt="Gemini QnA"
                                    sx={{
                                        width: "100%",
                                        height: 180,
                                        objectFit: "cover",
                                        borderRadius: 2,
                                        mb: 2
                                    }}
                                />


                                <Typography variant="h6" fontWeight="bold" gutterBottom>
                                    Gemini QnA
                                </Typography>

                                <Typography color="text.secondary" sx={{ mb: 2 }}>
                                    AI-powered question answering platform using Gemini API.
                                </Typography>

                                <Button
                                    variant="outlined"
                                    href="https://geminiqna.netlify.app/"
                                    target="_blank"
                                >
                                    Visit Project
                                </Button>
                            </Card>
                        </Grid>

                        {/* DraftWise */}
                        <Grid size={{ xs: 12, md: 4 }}>
                            <Card
                                sx={{
                                    p: 4,
                                    textAlign: "center",
                                    borderRadius: 3,
                                    transition: "0.3s",
                                    "&:hover": {
                                        transform: "translateY(-8px)",
                                        boxShadow: 6,
                                    },
                                }}
                            >
                                {/* <TrendingUpIcon sx={{ fontSize: 50, mb: 2 }} color="primary" /> */}
                                <Box
                                    component="img"
                                    src={draftwiseImg}
                                    alt="DraftWise"
                                    sx={{
                                        width: "100%",
                                        height: 180,
                                        objectFit: "cover",
                                        borderRadius: 2,
                                        mb: 2
                                    }}
                                />

                                <Typography variant="h6" fontWeight="bold" gutterBottom>
                                    DraftWise
                                </Typography>

                                <Typography color="text.secondary" sx={{ mb: 2 }}>
                                    Intelligent AI email drafting assistant built with Spring Boot & React.
                                </Typography>

                                <Button
                                    variant="outlined"
                                    href="https://draftwise.netlify.app/"
                                    target="_blank"
                                >
                                    Visit Project
                                </Button>
                            </Card>
                        </Grid>

                        {/* YumeCraft */}
                        <Grid size={{ xs: 12, md: 4 }}>
                            <Card
                                sx={{
                                    p: 4,
                                    textAlign: "center",
                                    borderRadius: 3,
                                    transition: "0.3s",
                                    "&:hover": {
                                        transform: "translateY(-8px)",
                                        boxShadow: 6,
                                    },
                                }}
                            >
                                {/* <BarChartIcon sx={{ fontSize: 50, mb: 2 }} color="primary" /> */}

                                <Box
                                    component="img"
                                    src={yumecraftImg}
                                    alt="YumeCraft"
                                    sx={{
                                        width: "100%",
                                        height: 180,
                                        objectFit: "cover",
                                        borderRadius: 2,
                                        mb: 2
                                    }}
                                />

                                <Typography variant="h6" fontWeight="bold" gutterBottom>
                                    YumeCraft
                                </Typography>

                                <Typography color="text.secondary" sx={{ mb: 2 }}>
                                    AI-driven creative content generation platform.
                                </Typography>

                                <Button
                                    variant="outlined"
                                    href="https://yumecraft.netlify.app/"
                                    target="_blank"
                                >
                                    Visit Project
                                </Button>
                            </Card>
                        </Grid>
                    </Grid>
                </Container>
            </Box>


            {/* Footer */}
            <Box
                component="footer"
                sx={{
                    backgroundColor: "#111",
                    color: "#fff",
                    mt: 8,
                    py: 4,
                    textAlign: "center",
                }}
            >
                <Typography variant="body2" sx={{ mb: 2 }}>
                    © 2026 Fitlexa. All rights reserved.
                </Typography>

                <Box
                    sx={{
                        display: "flex",
                        justifyContent: "center",
                        gap: 3,
                    }}
                >
                    <LinkedInIcon
                        sx={{ cursor: "pointer", "&:hover": { color: "#0A66C2" } }}
                        onClick={() => window.open("https://www.linkedin.com/in/likhith-raj005/", "_blank")}
                    />

                    <GitHubIcon
                        sx={{ cursor: "pointer", "&:hover": { color: "#fff" } }}
                        onClick={() => window.open("https://github.com/likhithraj005", "_blank")}
                    />

                    <InstagramIcon
                        sx={{ cursor: "pointer", "&:hover": { color: "#E1306C" } }}
                        onClick={() => window.open("https://www.instagram.com/likhithraj005/", "_blank")}
                    />

                    <LanguageIcon
                        sx={{ cursor: "pointer", "&:hover": { color: "#4CAF50" } }}
                        onClick={() => window.open("https://likhithraj.netlify.app/", "_blank")}
                    />
                </Box>
            </Box>

        </>
    );
};

export default LandingPage;

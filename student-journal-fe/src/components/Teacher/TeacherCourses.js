import React, { Component } from 'react'
import { Grid, Paper, Typography } from '@material-ui/core'
import ObligationItem from '../ObligationItem'
import Tabs from '@material-ui/core/Tabs'
import Tab from '@material-ui/core/Tab'

export default class TeacherCourse extends Component {

    render() {

        console.log('teacher course')
        console.log(this.props.course)
        const { description, name, semester, teacherName } = this.props.course

        return (
            <React.Fragment>
                <Paper elevation={24}>
                    <Grid container justify='center' direction='row' align-items='center'>
                        <Grid item>
                            <Grid container justify="center" direction="row">
                                <Grid item>
                                    <Typography color='secondary' variant="h4" gutterBottom>
                                        Ime kursa
                                    </Typography>
                                </Grid>
                            </Grid>
                            <Grid item>
                                <Grid container justify="center" direction="row">
                                    <Grid item>
                                        <Typography variant="h6" gutterBottom>
                                            {name}
                                        </Typography>
                                    </Grid>
                                </Grid>
                            </Grid>
                            <Grid item>
                                <Grid container justify="center" direction="row">
                                    <Grid item>
                                        <Typography color='secondary' variant="h4" gutterBottom>
                                            Semestar
                                    </Typography>
                                    </Grid>
                                </Grid>
                            </Grid>

                            <Grid item>
                                <Grid container justify="center" direction="row">
                                    <Grid item>
                                        <Typography variant="h6" gutterBottom>
                                            {semester}
                                        </Typography>
                                    </Grid>
                                </Grid>
                            </Grid>
                            <Grid item>
                                <Grid container justify="center" direction="row">
                                    <Grid item>
                                        <Typography color='secondary' variant="h4" gutterBottom>
                                            Opis
                    </Typography >
                                    </Grid>
                                </Grid>
                            </Grid>
                            <Grid item>
                                <Grid container justify="center" direction="row">
                                    <Grid item>
                                        <Typography variant="h5" gutterBottom>
                                            {description}
                                        </Typography >
                                    </Grid>
                                </Grid>
                                <Grid container justify="center" direction="row">
                                    <Grid item>
                                        <Typography color='secondary' variant="h4" gutterBottom>
                                            Ime predavaca
                                        </Typography >
                                    </Grid>
                                </Grid>

                                <Grid container justify="center" direction="row">
                                    <Grid item>
                                        <Typography variant="h5" gutterBottom>
                                            {teacherName}
                                        </Typography >
                                    </Grid>
                                </Grid>

                            </Grid>
                        </Grid>

                    </Grid>
                    <Grid container justify='center' direction='column' align-items='center'>
                        <React.Fragment>
                            <Grid item> <Tabs
                                indicatorColor="primary"
                                textColor="primary"
                                centered
                            >
                                <Tab label="" />
                                <Tab label="Istorijski pregled obaveza" />
                                <Tab label="" />
                            </Tabs></Grid>
                            {this.props.obligations.map((c) => (
                                <Grid item>  <ObligationItem key={c.description} obligat={c} /> </Grid>))}
                        </React.Fragment>
                    </Grid>
                </Paper>
            </React.Fragment >
        )
    }
}

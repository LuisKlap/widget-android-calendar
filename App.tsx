import React, { useState, useEffect } from 'react'
import { SafeAreaView, StyleSheet, TextInput, View, Button, Text } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from 'axios';
import { TOKEN } from '@env'

function App(): React.JSX.Element {
  const [username, setUsername] = useState('');
  const [loading, setLoading] = useState(false);
  const [dataFound, setDataFound] = useState<boolean | null>(null);

  useEffect(() => {
    const loadUsername = async () => {
      try {
        const savedUsername = await AsyncStorage.getItem('username');
        console.log('Username loaded from AsyncStorage:', savedUsername);
        if (savedUsername) {
          setUsername(savedUsername);
          fetchContributions(savedUsername);
        }
      } catch (error) {
        console.error('Failed to load username:', error);
      }
    };

    loadUsername();
  }, []);

  const handleUsernameChange = async (newUsername: string) => {
    setUsername(newUsername);
    console.log('Username changed:', newUsername);
    try {
      await AsyncStorage.setItem('username', newUsername);
      console.log('Username saved:', newUsername);
    } catch (error) {
      console.error('Failed to save username:', error);
    }
  };

  const fetchContributions = async (usernameToFetch: string) => {
    setLoading(true);
    setDataFound(null);
    console.log('Fetching contributions for:', usernameToFetch);

    const year = new Date().getFullYear();
    const since = `${year}-01-01T00:00:00Z`;
    const until = `${year}-12-31T23:59:59Z`;

    try {
      const reposResponse = await axios.get(`https://api.github.com/users/${usernameToFetch}/repos`, {
        headers: {
          Accept: 'application/vnd.github.v3+json',
          Authorization: `token ${TOKEN}`,
        },
      });
      const repos = reposResponse.data;

      let allCommitDates: string[] = [];

      for (const repo of repos) {
        let page = 1;
        let commits: any[] = [];
        while (true) {
          const commitsResponse = await axios.get(`https://api.github.com/repos/${usernameToFetch}/${repo.name}/commits?since=${since}&until=${until}&per_page=100&page=${page}`, {
            headers: {
              Accept: 'application/vnd.github.v3+json',
              Authorization: `token ${TOKEN}`,
            },
          });
          const newCommits = commitsResponse.data;
          if (newCommits.length === 0) {
            break;
          }
          commits = commits.concat(newCommits);
          page++;
        }
        const dates = commits.map((commit: any) => commit.commit.author.date);
        allCommitDates = allCommitDates.concat(dates);
      }

      allCommitDates.sort((a, b) => new Date(b).getTime() - new Date(a).getTime());

      setDataFound(allCommitDates.length > 0);
      console.log('Commit dates fetched:', allCommitDates);
    } catch (error) {
      console.error('Error fetching commit dates:', error);
      setDataFound(false);
    } finally {
      setLoading(false);
    }
  };

  return (
    <SafeAreaView style={styles.container}>
      <View style={styles.inputContainer}>
        <TextInput
          style={styles.input}
          placeholder="Your Username"
          placeholderTextColor="#888"
          value={username}
          onChangeText={handleUsernameChange}
        />
        <Button title="Fetch Contributions" onPress={() => fetchContributions(username)} />
      </View>
      {loading ? (
        <Text>Loading...</Text>
      ) : dataFound === null ? (
        <Text>Enter a username and press "Fetch Contributions"</Text>
      ) : dataFound ? (
        <Text>✅ Data found</Text>
      ) : (
        <Text>❌ No data found</Text>
      )}
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#808080',
    justifyContent: 'center',
    alignItems: 'center',
  },
  inputContainer: {
    width: '80%',
    marginTop: 20,
  },
  input: {
    height: 40,
    borderColor: '#000',
    borderWidth: 1,
    paddingHorizontal: 10,
    backgroundColor: '#fff',
    marginBottom: 10,
  },
});

export default App;